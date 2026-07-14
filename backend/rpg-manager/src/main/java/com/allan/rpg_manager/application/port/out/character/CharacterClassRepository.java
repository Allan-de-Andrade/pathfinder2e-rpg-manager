package com.allan.rpg_manager.application.port.out.character;

import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;

import java.util.List;
import java.util.Optional;

public interface CharacterClassRepository {
    Optional<CharacterClass> findById(Long id);
    List<CharacterClass> findAll();
}
