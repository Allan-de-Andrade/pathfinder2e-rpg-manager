package com.allan.rpg_manager.application.dtos.character;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;

import java.util.List;

public record BibliographyResponse(
        Long id,
        String name,
        String description,
        List<SkillResponse> skills,
        List<Attribute> attributesFixedBuffer,
        Attribute attributeFreeBuffer,
        TalentResponse talent
) {
}
