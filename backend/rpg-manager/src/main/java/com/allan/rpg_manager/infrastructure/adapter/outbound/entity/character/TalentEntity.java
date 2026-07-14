package com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "talents")
@Getter
@Setter
@NoArgsConstructor
public class TalentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="level_required")
    private int levelRequired;
}
