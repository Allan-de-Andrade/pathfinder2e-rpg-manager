package com.allan.rpg_manager.application.dtos;

public record GoogleUserInfo(
    String subject,
    String email,
    String name,
    boolean emailVerified
) {
    
}
