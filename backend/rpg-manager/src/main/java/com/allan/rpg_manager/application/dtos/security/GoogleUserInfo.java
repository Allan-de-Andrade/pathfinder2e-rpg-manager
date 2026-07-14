package com.allan.rpg_manager.application.dtos.security;

public record GoogleUserInfo(
    String subject,
    String email,
    String name,
    boolean emailVerified
) {
    
}
