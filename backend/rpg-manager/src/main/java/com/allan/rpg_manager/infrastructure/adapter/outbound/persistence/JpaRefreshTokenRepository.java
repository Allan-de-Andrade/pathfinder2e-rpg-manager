package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.RefreshToken;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByUserId(UUID userId);
    Optional<RefreshToken> findByToken(String token);
}
