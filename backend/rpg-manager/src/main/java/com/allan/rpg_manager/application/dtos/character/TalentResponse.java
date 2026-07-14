package com.allan.rpg_manager.application.dtos.character;

public record TalentResponse(
        String name,
        String description,
        int levelRequired
) {
}
