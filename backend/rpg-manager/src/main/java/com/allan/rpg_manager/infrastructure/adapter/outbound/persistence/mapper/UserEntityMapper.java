package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.domains.userDomain.UserDomain;
import com.allan.rpg_manager.domains.userDomain.AuthProviders;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public UserDomain toDomain(UserEntity entity) {
        UserDomain domain = new UserDomain();
        domain.setId(entity.getId());
        domain.setUsername(entity.getUsername());
        domain.setEmail(entity.getEmail());
        if (entity.getPassword() != null) {
            domain.setPassword(entity.getPassword());
        }
        if (entity.getProviderSubject() != null && entity.getProvider() != null) {
            domain.setProvider(entity.getProviderSubject(), AuthProviders.valueOf(entity.getProvider()));
        }
        domain.setIsActive(entity.getIsActive());
        return domain;
    }

    public UserEntity toEntity(UserDomain domain) {
        UserEntity entity = new UserEntity(domain);
        entity.setId(domain.getId());
        return entity;
    }
}
