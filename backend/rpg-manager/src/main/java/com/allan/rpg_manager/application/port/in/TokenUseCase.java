package com.allan.rpg_manager.application.port.in;

import java.util.UUID;

import com.allan.rpg_manager.application.dtos.LoginResponse;

public interface TokenUseCase {
    LoginResponse generateTokens(UUID userId);
    LoginResponse refreshAcessToken(String refreshToken);
    void invalidateRefreshToken(String refreshToken);
}
