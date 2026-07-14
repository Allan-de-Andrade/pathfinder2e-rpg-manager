package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.impl.character;

import com.allan.rpg_manager.application.port.out.character.CharacterRepository;
import com.allan.rpg_manager.domains.characterDomain.Character;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.UserMapper;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.CharacterEntity;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.impl.UserRepositoryImplement;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa.CharacterRepositoryJPA;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.CharacterMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CharacterRepositoryImpl implements CharacterRepository {
    private final CharacterMapper characterMapper;
    private final UserMapper userMapper;
    private final UserRepositoryImplement userRepository;
    private final CharacterRepositoryJPA characterRepository;
    public CharacterRepositoryImpl(CharacterMapper characterMapper, UserMapper userMapper, UserRepositoryImplement userRepository, CharacterRepositoryJPA characterRepository){
        this.characterMapper = characterMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
    }

    @Override
    public Page<Character> findAllCharactersByUserId(UUID userId, Pageable page) {
        return characterRepository.findAllByOwner_Id(userId, page).map(characterMapper::toDomain);
    }

    @Override
    public void save(Character character) {
        //convert to entity but without owner
        CharacterEntity characterEntity = characterMapper.toEntity(character);

        // set owner to character
        UserEntity owner = userRepository.findById(character.getOwnerID()).map(userMapper::toEntity).get();
        characterEntity.setOwner(owner);

        characterRepository.save(characterEntity);
    }

    @Override
    public Character update(Character character, Long id) {
        CharacterEntity characterEntity = characterMapper.toEntity(character);
        characterEntity.setId(id);
        characterRepository.save(characterEntity);
        return characterMapper.toDomain(characterRepository.save(characterEntity));
    }

    @Override
    public Optional<Character> findById(Long id) {
        return characterRepository.findById(id).map(characterMapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        characterRepository.deleteById(id);
    }
}
