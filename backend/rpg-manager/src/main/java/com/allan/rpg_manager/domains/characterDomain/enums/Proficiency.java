package com.allan.rpg_manager.domains.characterDomain.enums;

public enum Proficiency {
    UNTRAINED(0),
    TRAINED(2),
    EXPERT(4),
    MASTER(6),
    LEGENDARY(8);

    private final int proficiencyBonus;

    Proficiency(int proficiencyBonus) {
        this.proficiencyBonus = proficiencyBonus;
    }
    public int getBonus() {
        return proficiencyBonus;
    }
}
