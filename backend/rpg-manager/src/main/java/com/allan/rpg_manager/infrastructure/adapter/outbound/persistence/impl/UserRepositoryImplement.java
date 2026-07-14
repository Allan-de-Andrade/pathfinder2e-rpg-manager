package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.impl;

import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.securityDomain.UserDomain;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa.JpaUserRepository;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.UserMapper;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImplement implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    public UserRepositoryImplement(JpaUserRepository jpaUserRepository,
                                   UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDomain save(UserDomain userDomain) {
        UserEntity savedEntity = jpaUserRepository.saveAndFlush(userMapper.toEntity(userDomain));
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<UserDomain> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public Optional<UserDomain> findById(UUID id) {
        return jpaUserRepository.findById(id).map(userMapper::toDomain);
    }

    public Optional<UserDomain> findByProviderSubject(String providerSubject) {
        return jpaUserRepository.findByProviderSubject(providerSubject).map(userMapper::toDomain);
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
