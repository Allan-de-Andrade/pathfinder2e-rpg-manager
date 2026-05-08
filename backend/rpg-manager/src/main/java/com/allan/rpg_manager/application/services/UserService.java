package com.allan.rpg_manager.application.services;

import com.allan.rpg_manager.application.dtos.LoginRequest;
import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.dtos.UserRequest;
import com.allan.rpg_manager.application.port.in.UserUseCase;
import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.userDomain.UserDomain;

import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public UserDomain register(UserRequest userRequest) {
        if (userRequest == null) {
            throw new IllegalArgumentException("UserRequest cannot be null");
        }

        UserDomain userDomain = new UserDomain(
            userRequest.username(),
            userRequest.email(),
            passwordEncoder.encode(userRequest.password()),
            true
        );

        return userRepository.save(userDomain);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new IllegalArgumentException("LoginRequest cannot be null");
        }

        UserDomain userDomain = userRepository.findByEmail(loginRequest.email());

        if (userDomain == null || !userDomain.isLoginCorrect(loginRequest.email(), loginRequest.password(), passwordEncoder)) {
            throw new BadCredentialsException("Email or password is invalid");
        }

        return tokenService.generateToken(userDomain);
    }

    @Override
    @Transactional
    public UserDomain updateUser(UserRequest userRequest, UUID userId, UUID authenticatedUserId) {
        if (!userId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("You can only update your own account");
        }

        UserDomain userDomain = userRepository.findById(userId);

        if (userDomain == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }

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