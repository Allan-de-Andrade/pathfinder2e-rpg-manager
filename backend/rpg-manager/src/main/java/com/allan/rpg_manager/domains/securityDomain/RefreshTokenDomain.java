package com.allan.rpg_manager.domains.securityDomain;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;

@Getter
public class RefreshTokenDomain {
    private UUID userId;
    private String refreshToken;
    private Instant expiresAt;

    public RefreshTokenDomain(UUID userId,Long expiresIn) {
        this.userId = userId;
        this.refreshToken = UUID.randomUUID().toString();
        this.expiresAt = Instant.now().plusMillis(expiresIn);
    }
    public RefreshTokenDomain(UUID userId, String refreshToken, Instant expiresAt) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
