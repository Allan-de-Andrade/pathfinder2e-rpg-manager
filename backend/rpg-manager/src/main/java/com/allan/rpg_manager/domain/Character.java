package com.allan.rpg_manager.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.allan.rpg_manager.domain.services.CharacterRules;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class Character {
    private String name;
    private String background;
    private String personality;
    private int level;
    private CombatStatus combatStats; // status de combate do personagem
    private CharacterClass characterClass;
    private Ancestry ancestry; //raça do personagem
    private Bibliography bibliography;  //bibliografia (origem) do personagem
    private Map<Attribute,Integer> attributes; 
    private List<Skill> skills = new ArrayList<>();//pericias

    public Character(
        int level,
        Map<Attribute, Integer> attributes,
        Ancestry ancestry,
        CharacterClass characterClass,
        Bibliography bibliography
    ) {
        if(level < 1) {
            throw new IllegalArgumentException("Level must be at least 1");
        }
        this.level = level;
        this.attributes = attributes;
        this.ancestry = ancestry;
        this.characterClass = characterClass;
        this.bibliography = bibliography;
        this.skills.addAll(bibliography.getSkills());
    }

    public int setLevel(int level) {
        if(level < 1) {
            throw new IllegalArgumentException("Level must be at least 1");
        }
        this.level = level;
        return this.level;
    }

    //função para aplicar o bonus nos atributos
    public void applyAttributeBonus(Attribute attribute,Boolean increase) {
        Integer value = attributes.get(attribute);
        Integer bonus = new CharacterRules().calculateAttributeBonus(this,attribute, increase);
        attributes.put(attribute, value + bonus);
    }
    
    public void addProficiencySkill(Skill skill,Skill.SkillProficiency proficiency){
        Skill skillSet = skills.get(skills.indexOf(skill));
        if(skillSet.getProficiency().equals(proficiency)){
            return;
        }
        else{
            skill.setProficiency(proficiency);
        }
    }
}
