package com.allan.rpg_manager.infrastructure.adapter.outbound.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

import com.allan.rpg_manager.domains.userDomain.UserDomain;

@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public UserEntity(UserDomain userDomain){
        this.username = userDomain.getUsername();
        this.email = userDomain.getEmail();
        this.password = userDomain.getPassword();
    }
}
