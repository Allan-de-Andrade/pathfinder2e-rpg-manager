package com.allan.rpg_manager.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.allan.rpg_manager.domains.securityDomain.RefreshTokenDomain;

public interface RefreshTokenRepository {
    void saveRefreshToken(RefreshTokenDomain refreshTokenDomain);
    void deleteRefreshToken(String hashRefreshToken);
    Optional<RefreshTokenDomain> findByUserId(UUID userId);
    Optional<RefreshTokenDomain> findByTokenHash(String tokenHash);
}
