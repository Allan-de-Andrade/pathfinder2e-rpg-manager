package com.allan.rpg_manager.domains.characterDomain;
import java.util.*;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;

import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import com.allan.rpg_manager.domains.characterDomain.services.AttributeRules;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Heritage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Character {
    private Long id;
    private String name;
    private String backstory;
    private int maxHealth;
    private int currentHealth;
    private int level;
    private CharacterClass characterClass;
    private Ancestry ancestry; //raça do personagem
    private Bibliography bibliography;  //bibliografia (origem) do personagem
    private Map<Attribute, Integer> attributes;
    private Map<SkillType,Skill> skills = new HashMap<>();
    private List<Skill> skillsChoiced = new ArrayList<>(); // pericias extras escolhidas pelo usuario
    private int quantitySkillsExtra;
    private List<Talent> talents = new ArrayList<>();
    private Heritage heritage;
    private UUID ownerID;
    public Character(
            UUID ownerId,
            String name,
            String backstory,
            int level,
            Map<Attribute, Integer> attributes,
            Ancestry ancestry,
            CharacterClass characterClass,
            Bibliography bibliography
    ) {
        if (level < 1) {
            throw new IllegalArgumentException("Level must be at least 1");
        }
        this.ownerID = ownerId;
        this.name = name;
        this.backstory = backstory;
        this.level = level;
        this.attributes = attributes;
        this.ancestry = ancestry;
        this.characterClass = characterClass;
        this.bibliography = bibliography;
        this.skills = generateDefaultSkills();
        applySkills(characterClass.fixedSkills());
        applySkills(bibliography.skills());
    }
    public void completeCreation(AttributeRules attributeRules,List<SkillType> skillsExtra){
        this.attributes = attributeRules.calculateAttributes(this);
        this.maxHealth = attributeRules.calculateMaxHealthPoints(this);
        this.currentHealth = maxHealth;
        int intelligence = attributeRules.calculateModifier(attributes.get(Attribute.INTELLIGENCE));
        this.quantitySkillsExtra = characterClass.skillsExtra() + intelligence;
        applySkillsExtraClass(skillsExtra);
    }

    public Map<SkillType,Skill> generateDefaultSkills(){
        Map<SkillType,Skill> default_skills = new HashMap<>();
        for(SkillType skillType : SkillType.values()){
            default_skills.put(skillType,new Skill(skillType,Proficiency.UNTRAINED));
        }
        return default_skills;
    }

    public void applySkills(List<Skill> sourceSkills){
        for(Skill skill : sourceSkills){
            Skill characterSkill = this.skills.get(skill.getType());
            characterSkill.upgrade(skill.getProficiency());
            this.skills.replace(characterSkill.getType(),characterSkill);
        }
    }

    //function apply extra Skills from class choice by user
    public void applySkillsExtraClass(List<SkillType> extraSkills){
        List<Skill> skills = new ArrayList<>();

        for(SkillType skillType : extraSkills){
            Skill skill = new Skill(skillType,Proficiency.TRAINED);
            skills.add(skill);
        }

        if(extraSkills.size() == quantitySkillsExtra){
            applySkills(skills);
        }
        else
            throw new IllegalArgumentException("extra skills need to be " + quantitySkillsExtra + " skills for be applied");
    }

    public void setCurrentHealth(int health){
        if(health <= maxHealth && health > 0){
            this.currentHealth = health;
        }
    }
    public void setMaxHealth(int health){
        if(health > 0){
            this.maxHealth = health;
        }
    }
    public void setHeritage(Heritage heritage){
        if(heritage.getAncestryRequired().equals(ancestry))
            this.heritage = heritage;
    }
}
