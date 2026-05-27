package com.allan.rpg_manager.application.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.allan.rpg_manager.application.dtos.GoogleUserInfo;
import com.allan.rpg_manager.application.dtos.LoginRequest;
import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.port.in.AuthenticationUseCase;
import com.allan.rpg_manager.application.port.in.TokenUseCase;
import com.allan.rpg_manager.application.port.out.GoogleAuthPort;
import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.securityDomain.AuthProviders;
import com.allan.rpg_manager.domains.securityDomain.UserDomain;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationService implements AuthenticationUseCase {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenUseCase tokenUseCase;
    private final GoogleAuthPort googleAuthPort;

    public AuthenticationService(BCryptPasswordEncoder passwordEncoder, TokenService tokenService,
            GoogleAuthPort googleAuthPort,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUseCase = tokenService;
        this.googleAuthPort = googleAuthPort;
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

        if(!userDomain.get().getIsActive())
            throw new BadCredentialsException("User is not active");

        return tokenUseCase.generateTokens(userDomain.get().getId());
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
            return tokenUseCase.generateTokens(userDomain.get().getId());
        }

        userDomain = userRepository.findByEmail(googleUser.email());
        if (userDomain.isPresent()) {
            UserDomain existingUser = userDomain.get();
            existingUser.setProvider(googleUser.subject(), AuthProviders.Google);
            UserDomain userSaved = userRepository.save(existingUser);
            return tokenUseCase.generateTokens(userSaved.getId());
        }

        UserDomain userSave = UserDomain.googleUser(
            googleUser.name(),
            googleUser.email(),
            googleUser.subject()
        );
        userSave = userRepository.save(userSave);

        return tokenUseCase.generateTokens(userSave.getId());
    }
 
    @Override
    public void logout(String refreshToken) {
        if(refreshToken == null || refreshToken.isBlank())
            throw new IllegalArgumentException("Refresh token cannot be null or empty");

        tokenUseCase.invalidateRefreshToken(refreshToken);
    }
}
