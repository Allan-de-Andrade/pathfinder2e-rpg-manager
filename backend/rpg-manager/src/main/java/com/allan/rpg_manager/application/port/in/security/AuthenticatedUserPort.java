package com.allan.rpg_manager.application.port.in.security;
import java.util.UUID;

import org.springframework.security.core.Authentication;

public interface AuthenticatedUserPort {
    public UUID getAuthenticatedUserId(Authentication authentication);
}