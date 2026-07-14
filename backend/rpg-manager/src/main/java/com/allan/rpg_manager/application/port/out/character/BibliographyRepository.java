package com.allan.rpg_manager.application.port.out.character;

import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;

import java.util.List;
import java.util.Optional;

public interface BibliographyRepository {
    Optional<Bibliography> findById(Long id);
    List<Bibliography> findAll();
}
