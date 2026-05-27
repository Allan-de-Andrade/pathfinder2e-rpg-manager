package com.allan.rpg_manager.domains.characterDomain.enums;

public enum SkillProficiency {
    UNTRAINED(0),
    TRAINED(2),
    EXPERT(4),
    MASTER(6),
    LEGENDARY(8);

    private final int proficiencyBonus;

    SkillProficiency(int proficiencyBonus) {
        this.proficiencyBonus = proficiencyBonus;
    }
    public int getBonus() {
        return proficiencyBonus;
    }
}
