package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    public Optional<UserEntity> findByEmail(String email);
    public Optional<UserEntity> findByProviderSubject(String providerSubject);
    public void deleteById(UUID id);
}
