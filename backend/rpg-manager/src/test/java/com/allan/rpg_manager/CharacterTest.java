package com.allan.rpg_manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.allan.rpg_manager.domain.Ancestry;
import com.allan.rpg_manager.domain.Attribute;
import com.allan.rpg_manager.domain.Bibliography;
import com.allan.rpg_manager.domain.Character;
import com.allan.rpg_manager.domain.CharacterClass;
@ExtendWith(MockitoExtension.class)
public class CharacterTest {
    private Character character;
    @Mock
    private CharacterClass characterClass;
    @Mock
    private Ancestry ancestry;
    @Mock
    private Bibliography bibliography;
    @BeforeEach
    void setup(){
        Map<Attribute, Integer> attributes = 
        new HashMap<>();
        attributes.put(Attribute.STRENGTH, 10);
        attributes.put(Attribute.DEXTERITY, 10);
        attributes.put(Attribute.CONSTITUTION, 10);
        attributes.put(Attribute.INTELLIGENCE, 10);
        attributes.put(Attribute.WISDOM, 10);
        attributes.put(Attribute.CHARISMA, 10);

        character = new Character(5, attributes, ancestry, characterClass,bibliography);
    }
    @Test
    void testCalculateMaxHealthPoints() {
        when(characterClass.getHealthPoints()).thenReturn(10);
        when(ancestry.getHealthPoints()).thenReturn(5);
        Integer health = character.calculateMaxHealthPoints();
        assertEquals(55, health);
    }   
    @Test
    void testCalculateModifier(){
        Integer modifier = character.calculateModifier(Attribute.STRENGTH);
        assertEquals(0, modifier);
    }
    @Test
    void testCalculateAttributes(){
        List<Attribute> ancestryAttributes = List.of(Attribute.STRENGTH, Attribute.DEXTERITY);
        List<Attribute> bibliographyAttributes= List.of(Attribute.WISDOM,Attribute.INTELLIGENCE);
        Attribute bibliographyAttributeFreedom = Attribute.CHARISMA;

        when(ancestry.getAttributesBonus()).thenReturn(ancestryAttributes);
        when(ancestry.getAttributDebuffer()).thenReturn(Attribute.CONSTITUTION);
        when(characterClass.getPrimaryAttribute()).thenReturn(Attribute.STRENGTH);
        when(bibliography.getAttributeFreeBuffer()).thenReturn(bibliographyAttributeFreedom);
        when(bibliography.getAttributesFixedBuffer()).thenReturn(bibliographyAttributes);

        character.calculateAttributes();
        assertEquals(14, character.getAttributes().get(Attribute.STRENGTH));
        assertEquals(12, character.getAttributes().get(Attribute.DEXTERITY));
        assertEquals(8, character.getAttributes().get(Attribute.CONSTITUTION));
        assertEquals(12, character.getAttributes().get(Attribute.CHARISMA));
        assertEquals(12, character.getAttributes().get(Attribute.WISDOM));
        assertEquals(12, character.getAttributes().get(Attribute.INTELLIGENCE));

    }
}