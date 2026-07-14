package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.application.dtos.character.TalentResponse;
import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.TalentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TalentMapper {
    public TalentEntity toEntity(Talent talent) {
        if (talent == null) {
            return null;
        }

        TalentEntity entity = new TalentEntity();
        entity.setName(talent.getName());
        entity.setDescription(talent.getDescription());
        entity.setLevelRequired(talent.getLevelRequired());
        return entity;
    }

    public Talent toDomain(TalentEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Talent(
                entity.getName(),
                entity.getDescription(),
                entity.getLevelRequired(),
                Optional.empty()
        );
    }

    public List<TalentResponse> domainsToResponses(List<Talent> talents) {
        if (talents == null) {
            return List.of();
        }
        return talents.stream().map(this::toResponse).toList();
    }

    public TalentResponse toResponse(Talent talent) {
        if (talent == null) {
            return null;
        }
        return new TalentResponse(talent.getName(), talent.getDescription(), talent.getLevelRequired());
    }
}
