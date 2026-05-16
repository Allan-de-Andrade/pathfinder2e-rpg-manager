package com.allan.rpg_manager.application.services;

import com.allan.rpg_manager.application.dtos.GoogleUserInfo;
import com.allan.rpg_manager.application.dtos.LoginRequest;
import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.dtos.UserRequest;
import com.allan.rpg_manager.application.port.in.UserUseCase;
import com.allan.rpg_manager.application.port.out.GoogleAuthPort;
import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.userDomain.AuthProviders;
import com.allan.rpg_manager.domains.userDomain.UserDomain;
import com.allan.rpg_manager.infrastructure.adapter.inbound.web.handler.UserAlreadyRegistered;

import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final GoogleAuthPort googleAuthPort;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       TokenService tokenService,
                       GoogleAuthPort googleAuthPort) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.googleAuthPort = googleAuthPort;
    }

    @Override
    @Transactional
    public UserDomain register(UserRequest userRequest) {
        if (userRequest == null) {
            throw new IllegalArgumentException("UserRequest cannot be null");
        }
        userRepository.findByEmail(userRequest.email()).ifPresent(u -> {
            throw new UserAlreadyRegistered("Email is already in use: " + userRequest.email());
        });
        UserDomain userDomain = new UserDomain(
            userRequest.username(),
            userRequest.email(),
            passwordEncoder.encode(userRequest.password())
        );

        return userRepository.save(userDomain);
    }

    @Override
    public LoginResponse loginWithCredentials(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new IllegalArgumentException("LoginRequest cannot be null");
        }

        Optional<UserDomain> userDomain = userRepository.findByEmail(loginRequest.email());
        if (!userDomain.isPresent() || !isLoginCorrect(userDomain.get(), loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Email or password is invalid");
        }

        return tokenService.generateToken(userDomain.get());
    }
    
    public boolean isLoginCorrect(UserDomain userDomain, LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        if (userDomain == null || loginRequest == null) {
            throw new IllegalArgumentException("UserDomain and LoginRequest cannot be null");
        }
        return userDomain.getEmail().equals(loginRequest.email())
            && userDomain.getPassword() != null
            && passwordEncoder.matches(loginRequest.password(), userDomain.getPassword());
    }

    @Override
    @Transactional
    public LoginResponse loginWithGoogle(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        GoogleUserInfo googleUser = googleAuthPort.verifyIdToken(token);
        Optional<UserDomain> userDomain = userRepository.findByProviderSubject(googleUser.subject());
        
        if (userDomain.isPresent()) {
            return tokenService.generateToken(userDomain.get());
        }

        userDomain = userRepository.findByEmail(googleUser.email());
        if (userDomain.isPresent()) {
            UserDomain existingUser = userDomain.get();
            existingUser.setProvider(googleUser.subject(), AuthProviders.Google);
            UserDomain userSaved = userRepository.save(existingUser);
            return tokenService.generateToken(userSaved);
        }

        UserDomain userSave = UserDomain.googleUser(
            googleUser.name(),
            googleUser.email(),
            googleUser.subject()
        );
        userSave = userRepository.save(userSave);

        return tokenService.generateToken(userSave);
    }

    @Override
    @Transactional
    public UserDomain updateUser(UserRequest userRequest, UUID userId, UUID authenticatedUserId) {
        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update your own account");
        }

        Optional<UserDomain> userDomainOptional = userRepository.findById(userId);

        if (!userDomainOptional.isPresent()) {
            throw new IllegalArgumentException("User not found: " + userId);
        }

        UserDomain userDomain = userDomainOptional.get();
        userDomain.setUsername(userRequest.username());
        userDomain.setEmail(userRequest.email());
        userDomain.setPassword(passwordEncoder.encode(userRequest.password()));

        return userRepository.save(userDomain);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId, UUID authenticatedUserId) {
        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only delete your own account");
        }

        userRepository.deleteById(userId);
    }
}
