package com.allan.rpg_manager.application.port.in;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;

import com.allan.rpg_manager.application.dtos.LoginRequest;
import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.dtos.UserRequest;
import com.allan.rpg_manager.domains.userDomain.UserDomain;

public interface UserUseCase {
    public UserDomain register(UserRequest userRequest);
    public LoginResponse login(LoginRequest loginRequest);
    public UserDomain updateUser(UserRequest userRequest,UUID userId,UUID authenticatedUserId);
    public void deleteUser(UUID userId,UUID authenticatedUserId) throws AccessDeniedException;
}