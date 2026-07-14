package com.allan.rpg_manager.application.port.out.character;

import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;

import java.util.List;
import java.util.Optional;

public interface AncestryRepository {
    Optional<Ancestry> findById(Long id);
    List<Ancestry> findAll();
}
