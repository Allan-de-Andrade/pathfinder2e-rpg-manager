package com.allan.rpg_manager.application.services;

import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.port.in.TokenUseCase;
import com.allan.rpg_manager.domains.userDomain.UserDomain;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService implements TokenUseCase {

    private static final long EXPIRES_IN = 300L;

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public LoginResponse generateToken(UserDomain userDomain) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("rpg-back-end")
                .subject(userDomain.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRES_IN))
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(token, EXPIRES_IN);
    }
}