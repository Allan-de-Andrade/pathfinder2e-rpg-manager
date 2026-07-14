package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.AncestryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AncestryRepositoryJPA extends JpaRepository<AncestryEntity, Long> {
}
