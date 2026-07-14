package com.allan.rpg_manager.domains.characterDomain.valueObjects;

import java.util.List;
import java.util.Optional;

import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.domains.characterDomain.enums.AncestrySize;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.Language;

public record Ancestry(
        Long id,
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
