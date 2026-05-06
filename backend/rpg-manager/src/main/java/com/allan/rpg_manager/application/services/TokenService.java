package com.allan.rpg_manager.application.services;

import java.time.Instant;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.port.in.TokenUseCase;
import com.allan.rpg_manager.domains.userDomain.UserDomain;
import com.nimbusds.jwt.JWTClaimsSet;

public class TokenService implements TokenUseCase {
    private final JwtEncoder jwtEncoder;
    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    @Override
    public LoginResponse generateToken(UserDomain userDomain) {
        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder().
        issuer("rpg-back-end").
        subject(userDomain.getId().toString())
        .issuedAt(now).
        expiresAt(now.plusSeconds(expiresIn)).
        build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(  );
        return new LoginResponse(jwtValue, expiresIn);
    }
}
