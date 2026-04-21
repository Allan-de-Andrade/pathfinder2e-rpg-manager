package com.allan.rpg_manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class Talent {
    private String name;
    private String description;
    private int levelRequired;
    private Optional<Skill> requiredSkill;
}
