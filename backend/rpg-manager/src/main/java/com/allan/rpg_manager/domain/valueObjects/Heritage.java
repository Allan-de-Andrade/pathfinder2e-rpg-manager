package com.allan.rpg_manager.domain.valueObjects;

import com.allan.rpg_manager.domain.Talent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Heritage {
    private String name;
    private String description;
    private Talent talent;
}
