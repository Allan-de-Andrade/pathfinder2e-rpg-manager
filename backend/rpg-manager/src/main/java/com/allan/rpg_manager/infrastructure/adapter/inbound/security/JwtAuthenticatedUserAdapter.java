// infrastructure/adapter/inbound/security/JwtAuthenticatedUserAdapter.java
package com.allan.rpg_manager.infrastructure.adapter.inbound.security;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.allan.rpg_manager.application.port.in.security.AuthenticatedUserPort;

@Component
public class JwtAuthenticatedUserAdapter implements AuthenticatedUserPort {

    @Override
    public UUID getAuthenticatedUserId(Authentication authentication) {
        Jwt token = (Jwt) authentication.getPrincipal();
        return UUID.fromString(token.getSubject());
    }

}