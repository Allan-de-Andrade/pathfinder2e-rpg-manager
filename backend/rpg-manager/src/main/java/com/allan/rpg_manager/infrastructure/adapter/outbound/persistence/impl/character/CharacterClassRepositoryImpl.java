package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.impl.character;

import com.allan.rpg_manager.application.port.out.character.CharacterClassRepository;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa.CharacterClassRepositoryJPA;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.ClassMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CharacterClassRepositoryImpl implements CharacterClassRepository {
    private final CharacterClassRepositoryJPA characterClassRepositoryJPA;
    private final ClassMapper classMapper;

    public CharacterClassRepositoryImpl(CharacterClassRepositoryJPA characterClassRepositoryJPA, ClassMapper classMapper) {
        this.characterClassRepositoryJPA = characterClassRepositoryJPA;
        this.classMapper = classMapper;
    }

    @Override
    public Optional<CharacterClass> findById(Long id) {
        return characterClassRepositoryJPA.findById(id).map(classMapper::toDomain);
    }

    @Override
    public List<CharacterClass> findAll() {
        return characterClassRepositoryJPA.findAll().stream().map(classMapper::toDomain).toList();
    }
}
