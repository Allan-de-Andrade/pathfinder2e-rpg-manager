package com.allan.rpg_manager.application.dtos;

import java.util.UUID;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;

public record UserResponse(UUID id, String username, String email) {
 
    public UserResponse(UserEntity userEntity) {
        this(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());
    }
}
