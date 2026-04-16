package com.allan.rpg_manager.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class CharacterClass {
    private final String name;
    private final String description;
    private final List<Talent> talents;
    private Integer healthPoints;
    private Attribute primaryAttribute;
    public void setHealthPoints(Integer healthPoints) {
        if(healthPoints < 0) {
            throw new IllegalArgumentException("Health points cannot be negative");
        }
        this.healthPoints = healthPoints;
    }
    public void setPrimmaryAttribute(Attribute primaryAttribute){
        this.primaryAttribute = primaryAttribute;
    }
}
