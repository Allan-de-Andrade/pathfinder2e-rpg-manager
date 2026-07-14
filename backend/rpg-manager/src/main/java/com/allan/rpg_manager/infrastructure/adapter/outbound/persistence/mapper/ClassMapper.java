package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.application.dtos.character.ClassResponse;
import com.allan.rpg_manager.domains.characterDomain.Skill;
import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.ClassEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ClassMapper {

    private final TalentMapper talentMapper;
    private final SkillMapper skillMapper;
    public ClassMapper(TalentMapper talentMapper, SkillMapper skillMapper) {
        this.talentMapper = talentMapper;
        this.skillMapper = skillMapper;
    }

    public ClassEntity toEntity(CharacterClass characterClass) {
        if (characterClass == null) {
            return null;
        }

        ClassEntity entity = new ClassEntity();
        entity.setId(characterClass.id());
        entity.setName(characterClass.name());
        entity.setDescription(characterClass.description());
        entity.setTalents(characterClass.talents() == null
                ? Collections.emptyList()
                : characterClass.talents().stream().map(talentMapper::toEntity).collect(Collectors.toCollection(ArrayList::new)));
        entity.setHealthPoints(characterClass.healthPoints());
        entity.setPrimaryAttribute(characterClass.primaryAttribute());
        entity.setFixedSkills(skillMapper.toSkillMap(characterClass.fixedSkills()));
        entity.setSkillsExtra(characterClass.skillsExtra());
        return entity;
    }

    public CharacterClass toDomain(ClassEntity entity) {
        if (entity == null) {
            return null;
        }

        return new CharacterClass(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTalents() == null
                        ? Collections.emptyList()
                        : entity.getTalents().stream().map(talentMapper::toDomain).
                          collect(Collectors.toCollection(ArrayList::new)),
                entity.getHealthPoints(),
                entity.getPrimaryAttribute(),
                skillMapper.toSkillList(entity.getFixedSkills()),
                entity.getSkillsExtra()
        );
    }

    public ClassResponse toResponse(CharacterClass characterClass) {
        return new ClassResponse(
                characterClass.id(),
                characterClass.name(),
                characterClass.description(),
                talentMapper.domainsToResponses(characterClass.talents()),
                characterClass.healthPoints(),
                characterClass.primaryAttribute(),
                skillMapper.domainsToResponses(characterClass.fixedSkills()),
                characterClass.skillsExtra()
        );
    }
}
