package com.allan.rpg_manager.domain.services;

import java.util.Map;

import com.allan.rpg_manager.domain.Ancestry;
import com.allan.rpg_manager.domain.Attribute;
import com.allan.rpg_manager.domain.Bibliography;
import com.allan.rpg_manager.domain.Character;
import com.allan.rpg_manager.domain.CharacterClass;

public class CharacterRules {
    //Calcula os modificadores de atributos, ex:8 = -1, 10 = +0, 12 = +1 ...
    public Integer calculateModifier(Character character,Attribute attribute){
        Integer value = character.getAttributes().get(attribute);
        return (int) Math.floor(value / 2 - 5);
    }

    //Calcula os pontos de vida maximos de personagens considerando a Classe,
    //Ancestralidade,Level e modificadores de constituição
    public Integer calculateMaxHealthPoints(Character character) {
        Ancestry ancestry = character.getAncestry();
        CharacterClass characterClass = character.getCharacterClass();
        Integer healthPoints = ancestry.getHealthPoints() + characterClass.getHealthPoints();
        healthPoints += this.calculateModifier(character,Attribute.CONSTITUTION);
        healthPoints += characterClass.getHealthPoints() * (character.getLevel() - 1);
        return healthPoints;
    }

    //calcula os atributos considerando os bônus de Ancestralidade,Classe e Bibliografia
    public void calculateAttributes(Character character){
        Ancestry ancestry = character.getAncestry();
        CharacterClass characterClass = character.getCharacterClass();
        Bibliography bibliography = character.getBibliography();

        ancestry.getAttributesBonus().forEach(attribute -> {
            character.applyAttributeBonus(attribute, true);
        });
        
        bibliography.getAttributesFixedBuffer().forEach(attribute -> {
            character.applyAttributeBonus(attribute, true);
        });

        Attribute originBuffe = bibliography.getAttributeFreeBuffer();
        Attribute ancestryDebuffe = ancestry.getAttributDebuffer();
        Attribute classAttribute = characterClass.getPrimaryAttribute();
        
        character.applyAttributeBonus(originBuffe,true);
        character.applyAttributeBonus(ancestryDebuffe, false);
        character.applyAttributeBonus(classAttribute, true);
    }
  
    //Calcula quanto vai  ser o bonus/penalidade depedendo do valor do attributo
    Integer calculateAttributeBonus(Character character,Attribute attribute, Boolean increase) {

        Map<Attribute,Integer> attributes = character.getAttributes();
        int attributeValue = attributes.get(attribute);
        int bonus = 0;
        if(attributeValue >= 18){
            bonus +=1 * (increase ? 1 : -1);
        }
        else{
            bonus +=2 * (increase ? 1 : -1);
        }
        return bonus;
    }
}
