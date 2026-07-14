package com.allan.rpg_manager.application.dtos.character;

import com.allan.rpg_manager.domains.characterDomain.enums.AncestrySize;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.Language;

import java.util.List;

public record AncestryResponse(
        Long id,
        String name,
        AncestrySize size,
        int speed,
        int healthPoints,
        List<Language> languagesDefault,
        List<Language> languagesOptional,
        List<Attribute> attributesBonus,
        Attribute attributeDebuffer,
        List<TalentResponse> talents
) {
}
