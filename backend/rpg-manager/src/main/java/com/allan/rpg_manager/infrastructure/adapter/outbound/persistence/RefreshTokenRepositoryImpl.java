package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.allan.rpg_manager.application.port.out.RefreshTokenRepository;
import com.allan.rpg_manager.domains.securityDomain.RefreshTokenDomain;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.RefreshToken;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.RefreshTokenMapper;
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;    
    private final JpaUserRepository userRepository;

    public RefreshTokenRepositoryImpl(JpaRefreshTokenRepository jpaRefreshTokenRepository, RefreshTokenMapper refreshTokenMapper, JpaUserRepository userRepository) {
        this.refreshTokenRepository = jpaRefreshTokenRepository;
        this.refreshTokenMapper = refreshTokenMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void saveRefreshToken(RefreshTokenDomain refreshTokenDomain) {
        if(refreshTokenDomain == null) {
            throw new IllegalArgumentException("RefreshTokenDomain cannot be null");
        }
        
        UserEntity userEntity = userRepository.findById(refreshTokenDomain.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + refreshTokenDomain.getUserId()));

        refreshTokenRepository.findByUserId(refreshTokenDomain.getUserId())
            .ifPresent(refreshTokenRepository::delete);
        
        RefreshToken refreshTokenEntity = new RefreshToken(refreshTokenDomain,userEntity);
        refreshTokenRepository.save(refreshTokenEntity);
    }
    
    @Override
    public void deleteRefreshToken(String hashRefreshToken) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(hashRefreshToken);
        if (refreshTokenOpt.isPresent()) {
            refreshTokenRepository.delete(refreshTokenOpt.get());
        }
        else
            throw new IllegalArgumentException("Refresh token not found: " + hashRefreshToken);
    }
    
    @Override
    public Optional<RefreshTokenDomain> findByUserId(UUID userId) {
        return refreshTokenRepository.findByUserId(userId)
            .map(refreshTokenMapper::toDomain);
    }

    @Override
    public Optional<RefreshTokenDomain> findByTokenHash(String tokenHash) {
        return refreshTokenRepository.findByToken(tokenHash)
            .map(refreshTokenMapper::toDomain);
    }
}
