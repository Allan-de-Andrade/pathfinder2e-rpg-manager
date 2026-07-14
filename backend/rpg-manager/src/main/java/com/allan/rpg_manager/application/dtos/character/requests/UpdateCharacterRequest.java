package com.allan.rpg_manager.application.dtos.character.requests;

import com.allan.rpg_manager.domains.characterDomain.Skill;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;

import java.util.List;
import java.util.Map;

public record UpdateCharacterRequest (
    String name,
    String backstory,
    int level,
    Map<Attribute,Integer> attributes,
    Long classId,
    Long ancestryId,
    Long bibliographyId,
    List<Skill> skills
){}
