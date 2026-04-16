package com.allan.rpg_manager.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class Ancestry {
    private String name;
    private String description;
    private Size size;
    private int speed;
    private int healthPoints;
    private List<Languages> languagesDefault;
    private List<Languages> languagesOptional;
    private List<Talent> talents;
    private List<Attribute> attributesBonus;
    private Attribute attributDebuffer;
    public enum Size{
        SMALL,
        MEDIUM,
        LARGE
    }
    public enum Languages{
        TADALANO,
        ANÃO,
        ELFICO,
        GNOMO,
        HALFLING,
        ORC,
        SOMBRA,
        KELISH,
        DRACONICO,
        ABISSAL,
        CELESTIAL,
        INFERNAL,        
    }
}
