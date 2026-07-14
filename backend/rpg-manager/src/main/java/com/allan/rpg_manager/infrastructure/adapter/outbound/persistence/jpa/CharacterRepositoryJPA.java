package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//
public interface CharacterRepositoryJPA extends JpaRepository<CharacterEntity,Long> {
    Page<CharacterEntity> findAllByOwner_Id(UUID userId, Pageable pageable);
}
