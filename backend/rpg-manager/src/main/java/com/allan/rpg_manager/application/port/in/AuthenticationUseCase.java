package com.allan.rpg_manager.application.port.in;

import com.allan.rpg_manager.application.dtos.LoginRequest;
import com.allan.rpg_manager.application.dtos.LoginResponse;

public interface AuthenticationUseCase {
    LoginResponse loginWithCredentials(LoginRequest loginRequest);
    LoginResponse loginWithGoogle(String token);
    void logout(String token);
}