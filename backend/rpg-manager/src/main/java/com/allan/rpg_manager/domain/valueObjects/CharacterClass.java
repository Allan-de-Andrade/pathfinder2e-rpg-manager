package com.allan.rpg_manager.domain.valueObjects;

import java.util.List;

import com.allan.rpg_manager.domain.Talent;
import com.allan.rpg_manager.domain.enums.Attribute;

public record CharacterClass(
    String name,
    String description,
    List<Talent> talents,
    Integer healthPoints,
    Attribute primaryAttribute
)
{}
