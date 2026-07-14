package com.allan.rpg_manager.application.port.in.character;

import com.allan.rpg_manager.application.dtos.character.requests.CreateCharacterRequest;
import com.allan.rpg_manager.application.dtos.character.requests.UpdateCharacterRequest;
import com.allan.rpg_manager.domains.characterDomain.Character;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.UUID;


public interface CharacterUseCase {
    Page<Character> findAll(UUID userId,Pageable pageable);
    Character findById(UUID userId,Long id) throws AccessDeniedException;
    Map<Attribute,Integer> generateModifiers(Map<Attribute,Integer> attributes);
    Character newCharacter(CreateCharacterRequest createCharacterRequest, UUID user);
    Character update(UpdateCharacterRequest request, Long c_id, UUID userId) throws AccessDeniedException;
    void delete(Long idDelete,UUID userId) throws AccessDeniedException;
}
