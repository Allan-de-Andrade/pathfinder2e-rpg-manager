package com.allan.rpg_manager.infrastructure.adapter.outbound.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import com.allan.rpg_manager.domains.securityDomain.UserDomain;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable =true)
    private String password;

    @Column(nullable = true)
    private String providerSubject;
    @Column(nullable = true)
    private String provider;
    @Column(nullable = false)
    private Boolean isActive = true;
   
    public UserEntity(UserDomain userDomain){
        this.username = userDomain.getUsername();
        this.email = userDomain.getEmail();
        this.password = userDomain.getPassword();
        this.providerSubject = userDomain.getProviderSubject();
        if (userDomain.getProviders() != null && !userDomain.getProviders().isEmpty()) {
            this.provider = userDomain.getProviders().iterator().next().name();
        }
        this.isActive = userDomain.getIsActive();
    }
}
