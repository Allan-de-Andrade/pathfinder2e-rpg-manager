package com.allan.rpg_manager.application.services;

import com.allan.rpg_manager.application.dtos.security.LoginResponse;
import com.allan.rpg_manager.application.port.in.security.TokenUseCase;
import com.allan.rpg_manager.application.port.out.RefreshTokenRepository;
import com.allan.rpg_manager.domains.securityDomain.RefreshTokenDomain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TokenService implements TokenUseCase {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final long EXPIRES_IN;

    private final long REFRESH_EXPIRES_IN;
    
    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenService(
        JwtEncoder jwtEncoder,
        RefreshTokenRepository refreshTokenRepository,
        @Value("${jwt.access.expires.in}") long expires_IN,
        @Value("${jwt.refresh.expires.in}") long refresh_EXPIRES_IN
    ) {
        this.EXPIRES_IN = expires_IN;
        this.REFRESH_EXPIRES_IN = refresh_EXPIRES_IN;
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public LoginResponse generateTokens(UUID userId) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("rpg-back-end")
                .subject(userId.toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRES_IN))
                .build();

        Optional<RefreshTokenDomain> oldRefreshToken = refreshTokenRepository.findByUserId(userId);
        oldRefreshToken.ifPresent(t->refreshTokenRepository.deleteRefreshToken(t.getRefreshToken()));

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        String refreshToken = generateRefreshToken();
        saveRefreshToken(userId, hashRefreshToken(refreshToken));
        
        return new LoginResponse(token, refreshToken,
        REFRESH_EXPIRES_IN);
    }

    private String hashRefreshToken(String refreshToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }

    private String generateRefreshToken() {
        byte[] tokenBytes = new byte[32];
        SECURE_RANDOM.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private void saveRefreshToken(UUID userId, String tokenHash) {
        RefreshTokenDomain refreshTokenDomain = new RefreshTokenDomain(
            userId,
            tokenHash,
            Instant.now().plusSeconds(REFRESH_EXPIRES_IN)
        );

        refreshTokenRepository.saveRefreshToken(refreshTokenDomain);
    }

    @Override
    public LoginResponse refreshAcessToken(String refreshToken) {
        if(refreshToken == null || refreshToken.isBlank())
            throw new BadCredentialsException("refresh token cannot be empty or null");

        String previewHashToken = hashRefreshToken(refreshToken);
        RefreshTokenDomain refreshTokenDomain = refreshTokenRepository.findByTokenHash(previewHashToken).
                filter(token -> token.getExpiresAt().isAfter(Instant.now()))
                .orElseThrow(()-> new BadCredentialsException("Refresh Token invalid"));
        UUID userId = refreshTokenDomain.getUserId();

        invalidateRefreshToken(refreshToken);
        return generateTokens(userId);
    }

    @Override
    public void invalidateRefreshToken(String refreshToken){
        if(refreshToken == null || refreshToken.isBlank())
            throw new BadCredentialsException("refresh Token invalid");

        String hashRefreshToken = hashRefreshToken(refreshToken);
        if(!refreshTokenRepository.findByTokenHash(hashRefreshToken).isPresent())
            throw new BadCredentialsException("Refresh token don't exists");

        refreshTokenRepository.deleteRefreshToken(hashRefreshToken);
    }
}
