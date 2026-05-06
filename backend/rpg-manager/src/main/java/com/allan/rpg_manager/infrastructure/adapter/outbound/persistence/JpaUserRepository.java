package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    public Optional<UserEntity> findByEmail(String email);
    public void deleteById(UUID id);
}
