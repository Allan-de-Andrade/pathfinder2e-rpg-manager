package com.allan.rpg_manager.application.services;

import com.allan.rpg_manager.application.dtos.character.requests.CreateCharacterRequest;
import com.allan.rpg_manager.application.dtos.character.requests.UpdateCharacterRequest;
import com.allan.rpg_manager.application.port.in.character.CharacterUseCase;
import com.allan.rpg_manager.application.port.out.character.*;
import com.allan.rpg_manager.domains.characterDomain.Character;
import com.allan.rpg_manager.domains.characterDomain.CharacterFactory;
import com.allan.rpg_manager.domains.characterDomain.Skill;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import com.allan.rpg_manager.domains.characterDomain.services.AttributeRules;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;
@Service
public class CharacterService implements CharacterUseCase {
    private final AttributeRules attributeRules = new AttributeRules();
    private final CharacterFactory characterFactory;
    private final CharacterRepository characterRepository;
    private final CharacterClassRepository classRepository;
    private final AncestryRepository ancestryRepository;
    private final BibliographyRepository bibliographyRepository;

    public CharacterService(CharacterFactory characterFactory, CharacterRepository characterRepository, CharacterClassRepository classRepository, AncestryRepository ancestryRepository, BibliographyRepository bibliographyRepository) {
        this.characterFactory = characterFactory;
        this.characterRepository = characterRepository;
        this.classRepository = classRepository;
        this.ancestryRepository = ancestryRepository;
        this.bibliographyRepository = bibliographyRepository;
    }

    @Override
    public Page<Character> findAll(UUID userId, Pageable pageable) {
        return characterRepository.findAllCharactersByUserId(userId, pageable);
    }

    @Override
    public Character findById(UUID userId, Long characterId) throws AccessDeniedException {
        Optional<Character> character = characterRepository.findById(characterId);
        if (character.isEmpty())
            throw new BadCredentialsException("Character can't be empty");

        if (character.get().getOwnerID().equals(userId))
            return character.get();
        else
            throw new AccessDeniedException("you don't have access this character");
    }

    @Override
    public Map<Attribute, Integer> generateModifiers(Map<Attribute, Integer> attributes) {
        if (attributes.size() != Attribute.values().length)
            throw new IllegalArgumentException("none of attributes can't be empty");
        else {
            Map<Attribute, Integer> modifiers = new HashMap<>();
            attributes.forEach((attribute, value) -> {
                int modifier = attributeRules.calculateModifier(value);
                modifiers.put(attribute, modifier);
            });
            return modifiers;
        }
    }

    @Override
    public Character newCharacter(CreateCharacterRequest request, UUID user) {
        if (request == null)
            throw new IllegalArgumentException("request can't be null");

        CharacterClass characterClass = classRepository.findById(request.classId())
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
        Ancestry ancestry = ancestryRepository.findById(request.ancestryId())
                .orElseThrow(() -> new IllegalArgumentException("Ancestry not found"));
        Bibliography bibliography = bibliographyRepository.findById(request.bibliographyId())
                .orElseThrow(() -> new IllegalArgumentException("Bibliography not found"));

        Character character = characterFactory.create(
                user,
                request.name(),
                request.backstory(),
                request.level(),
                request.attributes(),
                request.skillsExtra(),
                characterClass,
                ancestry,
                bibliography
        );

        characterRepository.save(character);
        return character;
    }

    @Override
    public Character update(UpdateCharacterRequest request, Long c_id, UUID user_id) throws AccessDeniedException {
        Optional<Character> verifyCharacter = characterRepository.findById(c_id);

        if (request == null)
            throw new IllegalArgumentException("request can't be null");
        else if (verifyCharacter.isEmpty())
            throw new IllegalArgumentException("can't found character with this id");
        else if (!verifyCharacter.get().getOwnerID().equals(user_id))
            throw new AccessDeniedException("you can't update a character it's not yours");
        else {
            CharacterClass characterClass = classRepository.findById(request.classId())
                    .orElseThrow(() -> new IllegalArgumentException("Class not found"));
            Ancestry ancestry = ancestryRepository.findById(request.ancestryId())
                    .orElseThrow(() -> new IllegalArgumentException("Ancestry not found"));
            Bibliography bibliography = bibliographyRepository.findById(request.bibliographyId())
                    .orElseThrow(() -> new IllegalArgumentException("Bibliography not found"));

            List<SkillType> skillTypes = request.skills().stream()
                    .map(Skill::getType)
                    .toList();

            Character character = characterFactory.create(
                    user_id,
                    request.name(),
                    request.backstory(),
                    request.level(),
                    request.attributes(),
                    skillTypes,
                    characterClass,
                    ancestry,
                    bibliography
            );

            character.applySkills(request.skills());
            characterRepository.save(character);
            return character;
        }
    }

    @Override
    public void delete(Long idDelete, UUID userId) throws AccessDeniedException {
        Optional<Character> character = characterRepository.findById(idDelete);
        if (character.isEmpty())
            throw new IllegalArgumentException("can't found character with this idDelete");
        else if (!character.get().getOwnerID().equals(userId)) {
            throw new AccessDeniedException("you can't delete character it's not yours");
        } else {
            characterRepository.delete(idDelete);
        }
    }
}
