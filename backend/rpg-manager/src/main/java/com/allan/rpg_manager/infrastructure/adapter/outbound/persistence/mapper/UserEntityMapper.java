package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.domains.userDomain.UserDomain;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public UserDomain toDomain(UserEntity entity) {
        UserDomain domain = new UserDomain();
        domain.setId(entity.getId());
        domain.setUsername(entity.getUsername());
        domain.setEmail(entity.getEmail());
        domain.setPassword(entity.getPassword());
        return domain;
    }

    public UserEntity toEntity(UserDomain domain) {
        return new UserEntity(domain);
    }
}