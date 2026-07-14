package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.BibliographyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliographyRepositoryJPA extends JpaRepository<BibliographyEntity, Long> {
}
