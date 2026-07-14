package com.allan.rpg_manager.application.port.in.security;

import com.allan.rpg_manager.application.dtos.security.LoginRequest;
import com.allan.rpg_manager.application.dtos.security.LoginResponse;

public interface AuthenticationUseCase {
    LoginResponse loginWithCredentials(LoginRequest loginRequest);
    LoginResponse loginWithGoogle(String token);
    void logout(String token);
}