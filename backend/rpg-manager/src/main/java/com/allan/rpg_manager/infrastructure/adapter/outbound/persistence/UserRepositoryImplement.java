package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.userDomain.UserDomain;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;;

@Repository
public class UserRepositoryImplement implements UserRepository{
    private final JpaUserRepository jpaUserRepository;
    public UserRepositoryImplement(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public UserDomain save(UserDomain userDomain) {
        try{
            if(userDomain == null){
                throw new IllegalArgumentException("UserDomain cannot be null");
            }
            UserEntity userEntity = new UserEntity(userDomain);
            UserEntity savedEntity = jpaUserRepository.save(userEntity);
            userDomain.setId(savedEntity.getId());
            return userDomain;
        }
        catch(Exception e){
            throw new RuntimeException("Error saving user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDomain findByEmail(String email) {
        try{
            if(email.isEmpty() || email.isBlank())
                throw new IllegalArgumentException("Email cannot be empty");
            
            UserEntity userEntity = jpaUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
            UserDomain userDomain = new UserDomain();
            
            userDomain.setId(userEntity.getId());
            userDomain.setUsername(userEntity.getUsername());
            userDomain.setEmail(userEntity.getEmail());
            userDomain.setPassword(userEntity.getPassword());

            return userDomain;
        }
        catch(Exception e){
            throw new RuntimeException("Error finding user by username: " + e.getMessage(), e);
        }
    }
    @Override
    public void deleteById(UUID id){
        Optional<UserEntity> userDomain = jpaUserRepository.findById(id);
        
        if(userDomain.isPresent()){
            jpaUserRepository.deleteById(id);
        }
        else
            throw new RuntimeException("User not found with id: " + id);
    }
    @Override
    public UserDomain updateUser(UserDomain userRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }
    
}
