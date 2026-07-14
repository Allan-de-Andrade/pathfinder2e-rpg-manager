package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.impl.character;

import com.allan.rpg_manager.application.port.out.character.BibliographyRepository;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa.BibliographyRepositoryJPA;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.BibliographyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BibliographyRepositoryImpl implements BibliographyRepository {
    private final BibliographyRepositoryJPA bibliographyRepositoryJPA;
    private final BibliographyMapper bibliographyMapper;

    public BibliographyRepositoryImpl(BibliographyRepositoryJPA bibliographyRepositoryJPA, BibliographyMapper bibliographyMapper) {
        this.bibliographyRepositoryJPA = bibliographyRepositoryJPA;
        this.bibliographyMapper = bibliographyMapper;
    }

    @Override
    public Optional<Bibliography> findById(Long id) {
        return bibliographyRepositoryJPA.findById(id).map(bibliographyMapper::toDomain);
    }

    @Override
    public List<Bibliography> findAll() {
        return bibliographyRepositoryJPA.findAll().stream().map(bibliographyMapper::toDomain).toList();
    }
}
