package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterClassRepositoryJPA extends JpaRepository<ClassEntity, Long> {
}
