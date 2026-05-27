package com.allan.rpg_manager.infrastructure.adapter.inbound.web.mapper;
import org.springframework.stereotype.Component;

import com.allan.rpg_manager.application.dtos.UserResponse;
import com.allan.rpg_manager.domains.securityDomain.UserDomain;

@Component
public class UserMapper{
    public UserResponse toResponse(UserDomain user) {
            return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}