package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.impl.character;

import com.allan.rpg_manager.application.port.out.character.TalentRepository;
import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa.TalentRepositoryJPA;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.TalentMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TalentRepositoryImpl implements TalentRepository {
    private final TalentRepositoryJPA talentRepositoryJPA;
    private final TalentMapper talentMapper;

    public TalentRepositoryImpl(TalentRepositoryJPA talentRepositoryJPA, TalentMapper talentMapper) {
        this.talentRepositoryJPA = talentRepositoryJPA;
        this.talentMapper = talentMapper;
    }

    @Override
    public List<Talent> findAll() {
        return talentRepositoryJPA.findAll().stream().map(talentMapper::toDomain).toList();
    }

    @Override
    public List<Talent> findByClassId(Long classId) {
        return talentRepositoryJPA.findAllByClassId(classId).stream().map(talentMapper::toDomain).toList();
    }

    @Override
    public List<Talent> findByBibliographyId(Long bibliographyId) {
        return talentRepositoryJPA.findAllByBibliographyId(bibliographyId).stream().map(talentMapper::toDomain).toList();
    }

    @Override
    public List<Talent> findByAncestryId(Long ancestryId) {
        return talentRepositoryJPA.findAllByAncestryId(ancestryId).stream().map(talentMapper::toDomain).toList();
    }
}
