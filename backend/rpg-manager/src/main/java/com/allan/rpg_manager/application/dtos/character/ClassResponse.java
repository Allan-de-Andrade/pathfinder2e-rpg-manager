package com.allan.rpg_manager.application.dtos.character;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;

import java.util.List;

public record ClassResponse(
        Long id,
        String name,
        String description,
        List<TalentResponse> talents,
        Integer healthPoints,
        Attribute primaryAttribute,
        List<SkillResponse> fixedSkills,
        Integer skillsExtra
) {
}
