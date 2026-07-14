package com.allan.rpg_manager.infrastructure.adapter.inbound.web;

import com.allan.rpg_manager.application.dtos.security.UserRequest;
import com.allan.rpg_manager.application.dtos.security.UserResponse;
import com.allan.rpg_manager.application.port.in.security.AuthenticatedUserPort;
import com.allan.rpg_manager.application.port.in.security.UserUseCase;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.UserMapper;

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
