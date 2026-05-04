package com.allan.rpg_manager.domains.characterDomain.enums;

public enum SkillType {
    ACROBATICS(Attribute.DEXTERITY),
    ARCANA(Attribute.INTELLIGENCE),
    ATHLETICS(Attribute.STRENGTH),
    CRAFTING(Attribute.INTELLIGENCE),
    DECEPTION(Attribute.CHARISMA),
    DIPLOMACY(Attribute.CHARISMA),
    INTIMIDATION(Attribute.CHARISMA),
    LORE(Attribute.INTELLIGENCE),
    MEDICINE(Attribute.WISDOM),
    NATURE(Attribute.WISDOM),
    OCCULTISM(Attribute.INTELLIGENCE),
    PERFORMANCE(Attribute.CHARISMA),
    RELIGION(Attribute.WISDOM),
    SOCIETY(Attribute.INTELLIGENCE),
    STEALTH(Attribute.DEXTERITY),
    SURVIVAL(Attribute.WISDOM),
    THIEVERY(Attribute.DEXTERITY);

    private final Attribute attribute;

    SkillType(Attribute attribute) {
        this.attribute = attribute;
    }
    public Attribute getAttribute(){
        return attribute;
    }
}
