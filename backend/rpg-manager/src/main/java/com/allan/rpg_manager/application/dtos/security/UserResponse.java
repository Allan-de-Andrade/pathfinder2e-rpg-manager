package com.allan.rpg_manager.application.dtos.security;

import java.util.UUID;


public record UserResponse(UUID id, String username, String email) {
 
}
