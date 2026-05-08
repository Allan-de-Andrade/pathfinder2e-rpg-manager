package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence;

import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.userDomain.UserDomain;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.UserEntityMapper;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepositoryImplement implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper userEntityMapper;

    public UserRepositoryImplement(JpaUserRepository jpaUserRepository,
                                   UserEntityMapper userEntityMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public UserDomain save(UserDomain userDomain) {
        UserEntity savedEntity = jpaUserRepository.save(userEntityMapper.toEntity(userDomain));
        userDomain.setId(savedEntity.getId());
        return userDomain;
    }

    @Override
    public UserDomain findByEmail(String email) {
        UserEntity userEntity = jpaUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return userEntityMapper.toDomain(userEntity);
    }

    @Override
    public UserDomain findById(UUID id) {
        UserEntity userEntity = jpaUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return userEntityMapper.toDomain(userEntity);
    }

    @Override
    public UserDomain updateUser(UserDomain userDomain) {
        UserEntity userEntity = jpaUserRepository.findById(userDomain.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userDomain.getId()));

        userEntity.setEmail(userDomain.getEmail());
        userEntity.setUsername(userDomain.getUsername());
        userEntity.setPassword(userDomain.getPassword());

        jpaUserRepository.save(userEntity);
        return userDomain;
    }

    @Override
    public void deleteById(UUID id) {
        UserEntity userEntity = jpaUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        userEntity.setIsActive(false);
        jpaUserRepository.save(userEntity);
    }
}