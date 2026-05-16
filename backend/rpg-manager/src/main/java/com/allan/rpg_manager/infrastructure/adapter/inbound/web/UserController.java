package com.allan.rpg_manager.infrastructure.adapter.inbound.web;

import com.allan.rpg_manager.application.dtos.LoginRequest;
import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.dtos.GoogleLoginRequest;
import com.allan.rpg_manager.application.dtos.UserRequest;
import com.allan.rpg_manager.application.dtos.UserResponse;
import com.allan.rpg_manager.application.port.in.AuthenticatedUserPort;
import com.allan.rpg_manager.application.port.in.UserUseCase;
import com.allan.rpg_manager.infrastructure.adapter.inbound.web.mapper.UserMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserUseCase userUseCase;
    private final AuthenticatedUserPort authenticatedUserPort;
    private final UserMapper userMapper;

    public UserController(UserUseCase userUseCase,
                          AuthenticatedUserPort authenticatedUserPort,
                          UserMapper userMapper) {
        this.userUseCase = userUseCase;
        this.authenticatedUserPort = authenticatedUserPort;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        UserResponse response = userMapper.toResponse(userUseCase.register(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userUseCase.loginWithCredentials(loginRequest));
    }

    @PostMapping("/google-login")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        return ResponseEntity.ok(userUseCase.loginWithGoogle(request.idToken()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest,
                                                   @PathVariable UUID id,
                                                   Authentication authentication) {
        UUID idAuthenticated = authenticatedUserPort.getAuthenticatedUserId(authentication);
        UserResponse response = userMapper.toResponse(userUseCase.updateUser(userRequest, id, idAuthenticated));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id,
                                           Authentication authentication) {
        UUID idAuthenticated = authenticatedUserPort.getAuthenticatedUserId(authentication);
        userUseCase.deleteUser(id, idAuthenticated);
        return ResponseEntity.noContent().build();
    }
}
