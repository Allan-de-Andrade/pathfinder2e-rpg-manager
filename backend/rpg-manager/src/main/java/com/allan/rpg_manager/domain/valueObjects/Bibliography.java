package com.allan.rpg_manager.domain.valueObjects;

import java.util.List;

import com.allan.rpg_manager.domain.Skill;
import com.allan.rpg_manager.domain.Talent;
import com.allan.rpg_manager.domain.enums.Attribute;


public record Bibliography(
     String name,
     String description,
     List<Skill> skills,
     List<Attribute> attributesFixedBuffer,
     Attribute attributeFreeBuffer,
     Talent talent
){}
