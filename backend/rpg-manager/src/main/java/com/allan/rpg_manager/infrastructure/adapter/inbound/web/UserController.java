package com.allan.rpg_manager.infrastructure.adapter.inbound.web;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.dtos.UserRequest;
import com.allan.rpg_manager.application.dtos.UserResponse;
import com.allan.rpg_manager.domains.userDomain.UserDomain;
import com.allan.rpg_manager.application.port.in.UserUseCase;

@RequestMapping("/api/user/")
@RestController
public class UserController {
    
    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase){
        this.userUseCase = userUseCase;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest){
        try{
            UserDomain userDomain = new UserDomain(
                userRequest.username(), 
                userRequest.email(), 
                userRequest.password()
            );
            Optional<UserDomain> optionalUserDomain = Optional.of(userDomain);
            userDomain = userUseCase.register(optionalUserDomain);
            UserResponse userResponse = new UserResponse(userDomain.getId(), userDomain.getUsername(), userDomain.getEmail());
            return ResponseEntity.ok(userResponse);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }   
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authentication(@RequestBody UserRequest userRequest){
        UserDomain userDomain = new UserDomain(
            userRequest.username(), 
            userRequest.email(), 
            userRequest.password()
        );
        
        Optional<UserDomain> optionalUserDomain = Optional.of(userDomain);
        LoginResponse loginResponse = userUseCase.login(optionalUserDomain);
        return ResponseEntity.ok(loginResponse);
    }
}
