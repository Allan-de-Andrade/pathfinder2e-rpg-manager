package com.allan.rpg_manager.application.port.out.character;

import com.allan.rpg_manager.domains.characterDomain.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface CharacterRepository {
    Page<Character> findAllCharactersByUserId(UUID userId, Pageable page);
    void save(Character character);
    Character update(Character character, Long id);
    Optional<Character> findById(Long id);
    void delete(Long id);
}
