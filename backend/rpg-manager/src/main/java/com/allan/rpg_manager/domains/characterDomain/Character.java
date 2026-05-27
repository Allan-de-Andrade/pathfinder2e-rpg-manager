package com.allan.rpg_manager.domains.characterDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;

import com.allan.rpg_manager.domains.characterDomain.enums.SkillProficiency;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Heritage;
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
    private Map<Attribute, Integer> attributes;
    private List<Skill> skills = new ArrayList<>();//pericias
    private List<Talent> talents = new ArrayList<>();
    private Heritage heritage;

    public Character(
            int level,
            Map<Attribute, Integer> attributes,
            Ancestry ancestry,
            CharacterClass characterClass,
            Bibliography bibliography
    ) {
        if (level < 1) {
            throw new IllegalArgumentException("Level must be at least 1");
        }
        this.level = level;
        this.attributes = attributes;
        this.ancestry = ancestry;
        this.characterClass = characterClass;
        this.bibliography = bibliography;
        this.skills.addAll(bibliography.skills());
    }

    public void levelUp() {

    }

    //função para aplicar o bonus nos atributos
    public void applyAttributeBonus(Attribute attribute, Integer bonus) {
        attributes.compute(attribute, (k, value) -> value + bonus);
    }

    public void applyTalent(Talent talent) {
        if (getLevel() < talent.getLevelRequired()) return;

        Optional<Skill> requiredSkill = talent.getRequiredSkill();
        if(requiredSkill.isPresent()){
            boolean hasSkill = skills.stream().anyMatch(
                    c_skill -> c_skill.getType().equals(requiredSkill.get().getType()) &&
                            c_skill.getProficiency() != SkillProficiency.UNTRAINED
            );
            if(!hasSkill) return;
        }
        talents.add(talent);
    }
}
