package com.allan.rpg_manager.domain;

import com.allan.rpg_manager.domain.services.AttributeRules;

import com.allan.rpg_manager.domain.valueObjects.Ancestry;
import lombok.Getter;

@Getter
public class CombatStatus {
    private int maxHealthPoints;
    private final int speed;
    private Integer currentHealthPoints;
    private Integer armorClass;

    public CombatStatus(Character character) {
        maxHealthPoints = new AttributeRules().calculateMaxHealthPoints(character);
        currentHealthPoints = maxHealthPoints;
        Ancestry ancestry = character.getAncestry();
        this.speed = ancestry.speed();
    }
    public void setCurrentHealthPoints(Integer currentHealthPoints) {
        if(currentHealthPoints < 0 || currentHealthPoints > maxHealthPoints) {
            throw new IllegalArgumentException("Current health points must be between 0 and max health points");
        }
        this.currentHealthPoints = currentHealthPoints;
    }
    public void setMaxHealthPoints(Integer health){
        if(health < 0)
            throw new IllegalArgumentException("Max Health cannot be less than 0");
        this.maxHealthPoints = health;
    }
}
