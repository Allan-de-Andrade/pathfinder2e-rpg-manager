package com.allan.rpg_manager.application.dtos.character;

import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;

public record SkillResponse(
        SkillType type,
        Proficiency proficiency
) {
}
