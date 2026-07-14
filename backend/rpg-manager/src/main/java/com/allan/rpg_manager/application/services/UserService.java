package com.allan.rpg_manager.application.services;

import com.allan.rpg_manager.application.dtos.security.UserRequest;
import com.allan.rpg_manager.application.port.in.security.UserUseCase;
import com.allan.rpg_manager.application.port.out.GoogleAuthPort;
import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.securityDomain.UserDomain;
import com.allan.rpg_manager.infrastructure.adapter.inbound.web.handler.UserAlreadyRegistered;

import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       TokenService tokenService,
                       GoogleAuthPort googleAuthPort) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    @Override
    @Transactional
    public Optional<UserDomain> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");    
        }
        return userRepository.findById(id);
    }
}
