package com.allan.rpg_manager.domains.characterDomain.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.Character;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import org.springframework.stereotype.Service;

@Service
//Classe responsavel por gerir as regras dos atributos principais e secundarios do personagem
public class AttributeRules {
    //Calcula os modificadores de atributos, ex:8 = -1, 10 = +0, 12 = +1 ...
    public Integer calculateModifier(Integer value){
        return (int) Math.floor(value / 2 - 5);
    }

    //Calcula os pontos de vida maximos de personagens considerando a Classe,
    //Ancestralidade,Level e modificadores de constituição
    public Integer calculateMaxHealthPoints(Character character) {
        Ancestry ancestry = character.getAncestry();
        CharacterClass characterClass = character.getCharacterClass();
        Integer constitutionValue = character.getAttributes().get(Attribute.CONSTITUTION);

        Integer healthPoints = ancestry.healthPoints() + characterClass.healthPoints();
        healthPoints += this.calculateModifier(constitutionValue);
        healthPoints += characterClass.healthPoints() * (character.getLevel() - 1);
        return healthPoints;
    }

    //calcula os atributos considerando os bônus/debuffers de Ancestralidade,Classe e Bibliografia
    public Map<Attribute,Integer> calculateAttributes(Character character){
        Map<Attribute,Integer> attributesModifieds = new HashMap<>(character.getAttributes());

        List<Attribute> bibliographyBonus = character.getBibliography().attributesFixedBuffer();
        List<Attribute> ancestryBonus = character.getAncestry().attributesBonus();
        Attribute bibliographyFreeBonus = character.getBibliography().attributeFreeBuffer();
        Attribute classPrimaryAttribute = character.getCharacterClass().primaryAttribute();

        for (Attribute attribute : bibliographyBonus)
            returnBonusSum(attributesModifieds, attribute, true);


        for(Attribute attribute: ancestryBonus)
            returnBonusSum(attributesModifieds, attribute, true);

        character.getAncestry().attributeDebuffer().ifPresent
                (debuffer-> returnBonusSum(attributesModifieds,debuffer,false));

        returnBonusSum(attributesModifieds,classPrimaryAttribute,true);
        returnBonusSum(attributesModifieds,bibliographyFreeBonus,true);
        return attributesModifieds;
    }

    //Retorna os atributos somado com o bonus
    private Map<Attribute,Integer> returnBonusSum(Map<Attribute,Integer>attributes,Attribute attributeBuffer,boolean increase){
        int attributeValue = attributes.get(attributeBuffer);
        int bonus = calculateAttributeBonus(attributeValue, increase);
        attributes.compute(attributeBuffer,(k,v) ->attributeValue + bonus);
        return attributes;
    }

    //Calcula quanto vai  ser o bonus/penalidade depedendo do valor do attributo
    public Integer calculateAttributeBonus(int attributeValue,boolean increase) {
        int bonus = 0;
        if(attributeValue >= 18){
            bonus +=(increase ? 1 : -1);
        }
        else{
            bonus +=2 * (increase ? 1 : -1);
        }
        return bonus;
    }
}
