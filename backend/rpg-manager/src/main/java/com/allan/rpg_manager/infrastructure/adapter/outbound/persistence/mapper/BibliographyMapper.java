package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.application.dtos.character.BibliographyResponse;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.BibliographyEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class BibliographyMapper {
    private final TalentMapper talentMapper;
    private final SkillMapper skillMapper;
    public BibliographyMapper(TalentMapper talentMapper, SkillMapper skillMapper) {
        this.talentMapper = talentMapper;
        this.skillMapper = skillMapper;
    }

    public BibliographyEntity toEntity(Bibliography bibliography) {
        if (bibliography == null) {
            return null;
        }

        BibliographyEntity entity = new BibliographyEntity();
        entity.setId(bibliography.id());
        entity.setName(bibliography.name());
        entity.setDescription(bibliography.description());
        entity.setSkills(skillMapper.toSkillMap(bibliography.skills()));
        entity.setAttributesFixedBuffer(bibliography.attributesFixedBuffer() == null
                ? Collections.emptyList()
                : new ArrayList<>(bibliography.attributesFixedBuffer()));
        entity.setAttributeFreeBuffer(bibliography.attributeFreeBuffer());
        entity.setTalent(talentMapper.toEntity(bibliography.talent()));
        return entity;
    }

    public Bibliography toDomain(BibliographyEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Bibliography(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                skillMapper.toSkillList(entity.getSkills()),
                entity.getAttributesFixedBuffer() == null ? Collections.emptyList() : new ArrayList<>(entity.getAttributesFixedBuffer()),
                entity.getAttributeFreeBuffer(),
                talentMapper.toDomain(entity.getTalent())
        );
    }


    public BibliographyResponse toResponse(Bibliography bibliography) {
        return new BibliographyResponse(
                bibliography.id(),
                bibliography.name(),
                bibliography.description(),
                skillMapper.domainsToResponses(bibliography.skills()),
                bibliography.attributesFixedBuffer(),
                bibliography.attributeFreeBuffer(),
                talentMapper.toResponse(bibliography.talent())
        );
    }



}
