package com.allan.rpg_manager.infrastructure.adapter.inbound.web;

import com.allan.rpg_manager.application.dtos.character.requests.CreateCharacterRequest;
import com.allan.rpg_manager.application.dtos.character.CharacterResponse;
import com.allan.rpg_manager.application.port.in.character.CharacterUseCase;
import com.allan.rpg_manager.application.port.in.security.AuthenticatedUserPort;
import com.allan.rpg_manager.domains.characterDomain.Character;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.CharacterMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/character")
public class CharacterController {
    private final AuthenticatedUserPort authenticatedUserPort;
    private final CharacterUseCase characterUseCase;
    private final CharacterMapper characterMapper;
    public CharacterController(AuthenticatedUserPort authenticatedUserPort, CharacterUseCase characterUseCase, CharacterMapper characterMapper){
        this.authenticatedUserPort = authenticatedUserPort;
        this.characterUseCase = characterUseCase;
        this.characterMapper = characterMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharacterResponse>> findAllCharacters(Authentication authentication, @RequestParam(defaultValue = "0",required = false,value = "page") int page,@RequestParam(defaultValue = "5", required = false,value = "size") int size){
        UUID userId = authenticatedUserPort.getAuthenticatedUserId(authentication);
        PageRequest pageRequest = PageRequest.of(page,size);
        List<CharacterResponse> characters = characterUseCase.findAll(userId,pageRequest).
            stream().map(characterMapper::domainToResponse).toList();
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/get/{characterId}")
    public ResponseEntity<CharacterResponse> findById(Authentication authentication,@PathVariable Long characterId) throws AccessDeniedException {
        UUID userId = authenticatedUserPort.getAuthenticatedUserId(authentication);
        Character character = characterUseCase.findById(userId,characterId);
        CharacterResponse response = characterMapper.domainToResponse(character);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/modifiers")
    public ResponseEntity<Map<Attribute,Integer>> calculateModifiers(@RequestBody Map<Attribute,Integer> attributes){
        Map<Attribute,Integer> modifiers = characterUseCase.generateModifiers(attributes);
        return ResponseEntity.ok(modifiers);
    }

    @PostMapping("/save")
    public ResponseEntity<CharacterResponse> save(@RequestBody CreateCharacterRequest request, Authentication authentication) {
        UUID userId = authenticatedUserPort.getAuthenticatedUserId(authentication);
        Character character = characterUseCase.newCharacter(request, userId);
        CharacterResponse characterResponse = characterMapper.domainToResponse(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterResponse);
    }
    @DeleteMapping("/delete/{idCharacter}")
    public ResponseEntity<Void> delete(@PathVariable Long idCharacter,Authentication authentication) throws AccessDeniedException {
        UUID userId = authenticatedUserPort.getAuthenticatedUserId(authentication);
        characterUseCase.delete(idCharacter,userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
