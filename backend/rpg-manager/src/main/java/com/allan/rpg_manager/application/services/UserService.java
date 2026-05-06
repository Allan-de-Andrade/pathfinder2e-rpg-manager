package com.allan.rpg_manager.application.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.port.in.UserUseCase;
import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.userDomain.UserDomain;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder,TokenService tokenService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public UserDomain register(Optional<UserDomain> userRequest) {
        try{
            if(userRequest == null || !userRequest.isPresent()){
                throw new IllegalArgumentException("UserDomain cannot be null or empty");
            }
            UserDomain userDomain = userRepository.save(userRequest.get());
            return userDomain;
        }
        catch(Exception e){
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }

    @Override
    public LoginResponse login(Optional<UserDomain> userRequest) {
        try{
            if(userRequest.isEmpty() || !userRequest.isPresent()){
                throw new IllegalArgumentException("UserDomain cannot be null or empty");
            }

            UserDomain userDomain = userRepository.findByEmail(userRequest.get().getEmail());
        
            if(userDomain == null || !userDomain.isLoginCorrect(userRequest.get().getEmail(), 
            userRequest.get().getPassword(),passwordEncoder)){
                throw new BadCredentialsException("Email or password is invalid!");
            }
            LoginResponse loginResponse = tokenService.generateToken(userDomain);
            return loginResponse;
        }
        catch(Exception e){
            throw new RuntimeException("Error logging in user: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public UserDomain updateUser(Optional<UserDomain> userRequest, UUID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    @Transactional
    public Boolean deleteUser(UUID userId) {
        
        userRepository.deleteById(userId);
        return true;
    }
    
}
