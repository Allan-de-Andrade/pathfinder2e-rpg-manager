package com.allan.rpg_manager.application.dtos;

public record LoginResponse(String access_token,String refresh_token,Long refresh_expirationTime) {
} 