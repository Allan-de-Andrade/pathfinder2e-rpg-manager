package com.allan.rpg_manager.infrastructure.adapter.outbound.entity;

import java.time.Instant;
import java.util.UUID;

import com.allan.rpg_manager.domains.securityDomain.RefreshTokenDomain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    public UserEntity user;

    @Column(nullable = false, unique = true)
    public String token;

    @Column(nullable = false)
    public Instant expiryDate;

    public RefreshToken(RefreshTokenDomain refreshTokenDomain, UserEntity user) {
        this.user = user;
        this.token = refreshTokenDomain.getRefreshToken();
        this.expiryDate = refreshTokenDomain.getExpiresAt();
    }
}
