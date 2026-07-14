package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.application.dtos.character.AncestryResponse;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.AncestryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AncestryMapper {
    private final TalentMapper talentMapper;

    public AncestryMapper(TalentMapper talentMapper) {
        this.talentMapper = talentMapper;
    }

    public AncestryEntity toEntity(Ancestry ancestry) {
        if (ancestry == null) {
            return null;
        }

        AncestryEntity entity = new AncestryEntity();
        entity.setId(ancestry.id());
        entity.setName(ancestry.name());
        entity.setSize(ancestry.size());
        entity.setSpeed(ancestry.speed());
        entity.setHealthPoints(ancestry.healthPoints());
        entity.setLanguagesDefault(ancestry.languagesDefault() == null ? Collections.emptyList() : new ArrayList<>(ancestry.languagesDefault()));
        entity.setLanguagesOptional(ancestry.languagesOptional() == null ? Collections.emptyList() : new ArrayList<>(ancestry.languagesOptional()));
        entity.setTalents(ancestry.talents() == null
                ? Collections.emptyList()
                : ancestry.talents().stream().map(talentMapper::toEntity).collect(Collectors.toCollection(ArrayList::new)));
        entity.setAttributesBonus(ancestry.attributesBonus() == null ? Collections.emptyList() : new ArrayList<>(ancestry.attributesBonus()));
        entity.setHeritages(ancestry.heritages() == null ? Collections.emptyList() : new ArrayList<>(ancestry.heritages()));
        entity.setAttributeDebuffer(ancestry.attributeDebuffer().isEmpty() ? null : ancestry.attributeDebuffer().orElse(null));
        return entity;
    }

    public Ancestry toDomain(AncestryEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Ancestry(
                entity.getId(),
                entity.getName(),
                entity.getSize(),
                entity.getSpeed(),
                entity.getHealthPoints(),
                entity.getLanguagesDefault() == null ? Collections.emptyList() : new ArrayList<>(entity.getLanguagesDefault()),
                entity.getLanguagesOptional() == null ? Collections.emptyList() : new ArrayList<>(entity.getLanguagesOptional()),
                entity.getTalents() == null
                        ? Collections.emptyList()
                        : entity.getTalents().stream().map(talentMapper::toDomain).collect(Collectors.toCollection(ArrayList::new)),
                entity.getAttributesBonus() == null ? Collections.emptyList() : new ArrayList<>(entity.getAttributesBonus()),
                entity.getHeritages() == null ? Collections.emptyList() : new ArrayList<>(entity.getHeritages()),
                Optional.ofNullable(entity.getAttributeDebuffer())
        );
    }
    public AncestryResponse toResponse(Ancestry ancestry) {
        return new AncestryResponse(
                ancestry.id(),
                ancestry.name(),
                ancestry.size(),
                ancestry.speed(),
                ancestry.healthPoints(),
                ancestry.languagesDefault(),
                ancestry.languagesOptional(),
                ancestry.attributesBonus(),
                ancestry.attributeDebuffer().orElse(null),
                talentMapper.domainsToResponses(ancestry.talents())
        );
    }
}
