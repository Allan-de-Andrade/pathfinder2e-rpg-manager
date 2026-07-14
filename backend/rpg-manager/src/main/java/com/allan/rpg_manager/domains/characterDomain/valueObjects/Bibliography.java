package com.allan.rpg_manager.domains.characterDomain.valueObjects;

import java.util.List;
import java.util.Map;

import com.allan.rpg_manager.domains.characterDomain.Skill;
import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;


public record Bibliography(
        Long id,
     String name,
     String description,
     List<Skill> skills,
     List<Attribute> attributesFixedBuffer,
     Attribute attributeFreeBuffer,
     Talent talent
){}
