package com.allan.rpg_manager.domain;

import com.allan.rpg_manager.domain.services.CharacterRules;

import lombok.Getter;

@Getter
public class Skill {

    public enum SkillType{

        ACROBATICS(Attribute.DEXTERITY),
        ARCANA(Attribute.INTELLIGENCE),
        ATHLETICS(Attribute.STRENGTH),
        CRAFTING(Attribute.INTELLIGENCE),
        DECEPTION(Attribute.CHARISMA),
        DIPLOMACY(Attribute.CHARISMA),
        INTIMIDATION(Attribute.CHARISMA),
        LORE(Attribute.INTELLIGENCE),
        MEDICINE(Attribute.WISDOM),
        NATURE(Attribute.WISDOM),
        OCCULTISM(Attribute.INTELLIGENCE),
        PERFORMANCE(Attribute.CHARISMA),
        RELIGION(Attribute.WISDOM),
        SOCIETY(Attribute.INTELLIGENCE),
        STEALTH(Attribute.DEXTERITY),
        SURVIVAL(Attribute.WISDOM),
        THIEVERY(Attribute.DEXTERITY);

        private final Attribute attribute;

        private SkillType(Attribute attribute) {
            this.attribute = attribute;
        }
        public Attribute getAttribute(){
            return attribute;
        }
    }
    public enum SkillProficiency{
        UNTRAINED(0),
        TRAINED(2),
        EXPERT(4),
        MASTER(6),
        LEGENDARY(8);

        private final int proficiencyBonus;
        
        SkillProficiency(int proficiencyBonus) {
            this.proficiencyBonus = proficiencyBonus;
        }
        public int getBonus() {
            return proficiencyBonus;
        }
    }

    public Skill(SkillType type, SkillProficiency proficiency){
        this.type = type;
        this.proficiency = proficiency;
    }
    final private SkillType type;
    private SkillProficiency proficiency = SkillProficiency.UNTRAINED;

    public Integer calculateValue(Character character,Integer otherBonus){
        Integer attributeValue =  new CharacterRules().
        calculateModifier(character,type.getAttribute());

        Integer proficiencyBonus = proficiency.getBonus();
        Integer skillValue = attributeValue + proficiencyBonus + otherBonus;
        return skillValue;
    }
    
    public Integer calculateValue(Character character){
        return calculateValue(character,0);
    }

    public void setProficiency(SkillProficiency skillType) {
        this.proficiency = skillType;
    }
}
