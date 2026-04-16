package com.allan.rpg_manager.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Bibliography {
    private final String name;
    private final String description;
    private final List<Skill> skills;
    private final List<Attribute> attributesFixedBuffer;
    private Attribute attributeFreeBuffer;
    private final Talent talent;
    
    public void setAttributeFreeBuffer(Attribute attribute){
        this.attributeFreeBuffer = attribute;
    }
}
