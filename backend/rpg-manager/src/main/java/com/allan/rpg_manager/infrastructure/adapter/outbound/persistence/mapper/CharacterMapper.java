package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.application.dtos.character.CharacterResponse;
import com.allan.rpg_manager.domains.characterDomain.Character;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.CharacterEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CharacterMapper {
    private final AncestryMapper ancestryMapper;
    private final BibliographyMapper bibliographyMapper;
    private  final ClassMapper classMapper;
    private final SkillMapper skillMapper;

    public CharacterMapper(AncestryMapper ancestryMapper, BibliographyMapper bibliographyMapper, ClassMapper classMapper, SkillMapper skillMapper) {
        this.ancestryMapper = ancestryMapper;
        this.bibliographyMapper = bibliographyMapper;
        this.classMapper = classMapper;
        this.skillMapper = skillMapper;
    }

    public CharacterEntity toEntity(Character character){
        if (character == null) {
            throw new IllegalArgumentException("character can't be null");
        }

        CharacterEntity entity = new CharacterEntity();
        entity.setName(character.getName());
        entity.setBackstory(character.getBackstory());
        entity.setLevel(character.getLevel());
        entity.setCurrentHealth(character.getCurrentHealth());

        entity.setMaxHealth(character.getMaxHealth());
        entity.setAttributes(character.getAttributes());

        entity.setClassEntity(classMapper.toEntity(character.getCharacterClass()));
        entity.setAncestry(ancestryMapper.toEntity(character.getAncestry()));
        entity.setBibliography(bibliographyMapper.toEntity(character.getBibliography()));

        entity.setTalents(Collections.emptyList());
        entity.setSkills(skillMapper.toEntitySkills(character.getSkills()));
        return entity;
    }

    public Character toDomain(CharacterEntity entity){
        if (entity == null) {
            return null;
        }

        Character domain = new Character();

        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setBackstory(entity.getBackstory());
        domain.setLevel(entity.getLevel());

        domain.setAttributes(entity.getAttributes());
        domain.setMaxHealth(entity.getMaxHealth());
        domain.setCurrentHealth(entity.getCurrentHealth());

        domain.setAncestry(ancestryMapper.toDomain(entity.getAncestry()));
        domain.setBibliography(bibliographyMapper.toDomain(entity.getBibliography()));
        domain.setCharacterClass(classMapper.toDomain(entity.getClassEntity()));

        domain.setTalents(Collections.emptyList());
        domain.setSkills(skillMapper.toDomainSkills(entity.getSkills()));
        domain.setOwnerID(entity.getOwner().getId());
        return domain;
    }
    public CharacterResponse domainToResponse(Character domain){
        CharacterResponse response = new CharacterResponse();

        response.setId(domain.getId());
        response.setName(domain.getName());
        response.setBackstory(domain.getBackstory());
        response.setLevel(domain.getLevel());
        response.setAttributes(domain.getAttributes());

        response.setMaxHealth(domain.getMaxHealth());
        response.setCurrentHealth(domain.getCurrentHealth());

        response.setAncestryName(domain.getAncestry().name());
        response.setAncestryId(domain.getAncestry().id());

        response.setClassName(domain.getCharacterClass().name());
        response.setClassId(domain.getCharacterClass().id());

        response.setBibliographyName(domain.getBibliography().name());
        response.setBibliographyId(domain.getBibliography().id());

        response.setTalents(Collections.emptyList());
        response.setSkills(domain.getSkills());
        return response;
    }
}
