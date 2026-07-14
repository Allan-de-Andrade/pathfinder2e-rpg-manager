package com.allan.rpg_manager.domains.characterDomain;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import com.allan.rpg_manager.domains.characterDomain.services.AttributeRules;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CharacterFactory {

    private final AttributeRules attributeRules;

    public CharacterFactory(AttributeRules attributeRules) {
        this.attributeRules = attributeRules;
    }

    public Character create(
            UUID ownerId,
            String name,
            String backstory,
            int level,
            Map<Attribute,Integer> attributes,
            List<SkillType> skillTypes,
            CharacterClass characterClass,
            Ancestry ancestry,
            Bibliography bibliography
    ) {

        Character character = new Character(
                ownerId,
                name,
                backstory,
                level,
                attributes,
                ancestry,
                characterClass,
                bibliography
        );

        character.completeCreation(attributeRules,skillTypes);
        return character;
    }

}
