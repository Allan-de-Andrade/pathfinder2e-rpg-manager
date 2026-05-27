package com.allan.rpg_manager.domains.characterDomain;

import com.allan.rpg_manager.domains.characterDomain.enums.SkillProficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;

import lombok.Getter;

@Getter
public class Skill {
    public Skill(SkillType type, SkillProficiency proficiency){
        this.type = type;
        this.proficiency = proficiency;
    }
    final private SkillType type;
    private SkillProficiency proficiency = SkillProficiency.UNTRAINED;
   
    private int calculateValue(int attributeValue,int level){
        if(proficiency != SkillProficiency.UNTRAINED)
            return attributeValue + proficiency.getBonus() + level;
        return attributeValue + level;
    }
}
