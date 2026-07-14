package com.allan.rpg_manager.domains.characterDomain;

import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;

import lombok.Getter;

@Getter
public class Skill {
    public Skill(SkillType type, Proficiency proficiency){
        this.type = type;
        this.proficiency = proficiency;
    }
    final private SkillType type;
    private Proficiency proficiency = Proficiency.UNTRAINED;
   
    public int calculateValue(int attributeValue,int level){
        if(proficiency != Proficiency.UNTRAINED)
            return attributeValue + proficiency.getBonus() + level;
        return attributeValue + level;
    }
    public void upgrade(Proficiency newProficiency){
        if(newProficiency.ordinal() > this.proficiency.ordinal()){
            this.proficiency = newProficiency;
        }
    }
}
