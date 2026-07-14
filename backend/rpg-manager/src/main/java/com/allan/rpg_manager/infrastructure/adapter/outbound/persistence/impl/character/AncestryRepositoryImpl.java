package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.impl.character;

import com.allan.rpg_manager.application.port.out.character.AncestryRepository;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa.AncestryRepositoryJPA;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.AncestryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AncestryRepositoryImpl  implements AncestryRepository {
    private final AncestryRepositoryJPA ancestryRepositoryJPA;
    private final AncestryMapper ancestryMapper;

    public AncestryRepositoryImpl(AncestryRepositoryJPA ancestryRepositoryJPA, AncestryMapper ancestryMapper) {
        this.ancestryRepositoryJPA = ancestryRepositoryJPA;
        this.ancestryMapper = ancestryMapper;
    }

    @Override
    public Optional<Ancestry> findById(Long id) {
        return ancestryRepositoryJPA.findById(id).map(ancestryMapper::toDomain);
    }

    @Override
    public List<Ancestry> findAll() {
        return ancestryRepositoryJPA.findAll().stream().map(ancestryMapper::toDomain).toList();
    }
}
