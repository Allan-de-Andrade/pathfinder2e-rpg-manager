package com.allan.rpg_manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.allan.rpg_manager.domains.characterDomain.Character;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.services.AttributeRules;
@ExtendWith(MockitoExtension.class)
public class AttributeRulesTest {
    AttributeRules attributeRules = new AttributeRules();
    Character character;
    @Mock
    CharacterClass characterClass;
    @Mock
    Ancestry ancestry;
    @Mock
    Bibliography bibliography;
    @BeforeEach
    void setup(){
        Map<Attribute, Integer> attributes =
                new HashMap<>();
        attributes.put(Attribute.STRENGTH, 10);
        attributes.put(Attribute.DEXTERITY, 10);
        attributes.put(Attribute.CONSTITUTION, 10);
        attributes.put(Attribute.INTELLIGENCE, 10);
        attributes.put(Attribute.WISDOM, 10);
        attributes.put(Attribute.CHARISMA, 8);

        character = new Character(5, attributes, ancestry, characterClass,bibliography);
    }
    @Test
    void testCalculateMaxHealthPoints() {
        when(characterClass.healthPoints()).thenReturn(10);
        when(ancestry.healthPoints()).thenReturn(5);
        Integer health = attributeRules.calculateMaxHealthPoints(character);
        assertEquals(55, health);
    }
    @Test
    void testCalculateModifier(){
        int strengthValue = character.getAttributes().get(Attribute.STRENGTH);
        int charismaValue = character.getAttributes().get(Attribute.CHARISMA);
        Integer strengthModifier = attributeRules.calculateModifier(strengthValue);
        Integer charismaModifier = attributeRules.calculateModifier(charismaValue);
        assertEquals(0, strengthModifier);
        assertEquals(-1,charismaModifier);
    }
    @Test
    void testCalculateAttributes(){
        List<Attribute> ancestryAttributes = List.of(Attribute.STRENGTH, Attribute.DEXTERITY);
        List<Attribute> bibliographyAttributes= List.of(Attribute.DEXTERITY,Attribute.INTELLIGENCE);
        Attribute bibliographyAttributeFreedom = Attribute.STRENGTH;

        when(ancestry.attributesBonus()).thenReturn(ancestryAttributes);
        when(ancestry.attributeDebuffer()).thenReturn(Optional.of(Attribute.CHARISMA));
        when(characterClass.primaryAttribute()).thenReturn(Attribute.INTELLIGENCE);
        when(bibliography.attributeFreeBuffer()).thenReturn(bibliographyAttributeFreedom);
        when(bibliography.attributesFixedBuffer()).thenReturn(bibliographyAttributes);

        Map<Attribute,Integer>attributes = attributeRules.calculateAttributes(character);
        assertEquals(14,attributes.get(Attribute.STRENGTH));
        assertEquals(14,attributes.get(Attribute.DEXTERITY));
        assertEquals(14,attributes.get(Attribute.INTELLIGENCE));
        assertEquals(6,attributes.get(Attribute.CHARISMA));
    }
    @Test
    void testCalculateBonus(){
        int high_bonus = attributeRules.calculateAttributeBonus(14,true);
        int small_bonus = attributeRules.calculateAttributeBonus(20,true);
        int debuffer = attributeRules.calculateAttributeBonus(8,false);
        assertEquals(2,high_bonus);
        assertEquals(1,small_bonus);
        assertEquals(-2,debuffer);
    }
}
