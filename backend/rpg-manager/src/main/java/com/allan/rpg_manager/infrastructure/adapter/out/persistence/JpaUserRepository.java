package com.allan.rpg_manager.infrastructure.adapter.out.persistence;

import com.allan.rpg_manager.infrastructure.adapter.out.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
}
