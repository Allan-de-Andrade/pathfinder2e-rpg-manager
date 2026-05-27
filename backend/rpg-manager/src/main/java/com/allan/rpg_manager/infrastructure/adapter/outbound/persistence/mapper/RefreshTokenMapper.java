package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import org.springframework.stereotype.Component;

import com.allan.rpg_manager.domains.securityDomain.RefreshTokenDomain;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.RefreshToken;

@Component
public class RefreshTokenMapper {
    public RefreshTokenDomain toDomain(RefreshToken entity) {
        return new RefreshTokenDomain(
            entity.getUser().getId(),
            entity.getToken(),
            entity.getExpiryDate()
        );
    }
}
