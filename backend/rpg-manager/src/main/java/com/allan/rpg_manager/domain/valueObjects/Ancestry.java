package com.allan.rpg_manager.domain.valueObjects;

import java.util.List;
import java.util.Optional;

import com.allan.rpg_manager.domain.Talent;
import com.allan.rpg_manager.domain.enums.AncestrySize;
import com.allan.rpg_manager.domain.enums.Attribute;
import com.allan.rpg_manager.domain.enums.Language;

public record Ancestry(
        String name,
    AncestrySize size,
    int speed,
    int healthPoints,
    List<Language> languagesDefault,
    List<Language> languagesOptional,
    List<Talent> talents,
    List<Attribute> attributesBonus,
    List<Heritage> heritages,
    Optional<Attribute> attributeDebuffer
){}
