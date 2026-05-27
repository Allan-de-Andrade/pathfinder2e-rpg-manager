package com.allan.rpg_manager.domains.characterDomain.valueObjects;

import java.util.List;

import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;

public record CharacterClass(
    String name,
    String description,
    List<Talent> talents,
    Integer healthPoints,
    Attribute primaryAttribute
)
{}
