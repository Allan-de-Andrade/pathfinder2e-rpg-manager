package com.allan.rpg_manager.application.dtos.character;

import com.allan.rpg_manager.domains.characterDomain.Skill;
import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class CharacterResponse {
    private Long id;
    private String name;
    private String backstory;
    private int maxHealth;
    private int currentHealth;
    private int level;
    private String className;
    private String ancestryName;
    private String bibliographyName;
    private String heritageName;
    private Long classId;
    private Long ancestryId;
    private Long bibliographyId;
    private Long heritageId;
    private Map<Attribute, Integer> attributes;
    private Map<Attribute,Integer> modifiers;
    private Map<SkillType, Skill> skills = new HashMap<>();
    private List<Skill> skillsChoiced = new ArrayList<>(); // pericias extras escolhidas pelo usuario
    private List<Talent> talents = new ArrayList<>();
}
