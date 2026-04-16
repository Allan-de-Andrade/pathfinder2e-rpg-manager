package com.allan.rpg_manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Talent {
    private String name;
    private String description;
    private int levelRequired;
    private Skill requiredSkill; 

}
