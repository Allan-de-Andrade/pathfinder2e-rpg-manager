package com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Table(name="characters")
@Entity
@Getter
@Setter
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;
    @Column(name="backstory")
    private String backstory;

    @Column(name="level",nullable = false)
    private int level;

    @Column(name = "current_health",nullable = false)
    private int currentHealth;

    @Column(name = "max_health", nullable = false)
    private int maxHealth;

    @ElementCollection
    @CollectionTable(
            name="character_attributes",
            joinColumns = @JoinColumn(name="character_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="attribute")
    @Column(name="value")
    private Map<Attribute,Integer> attributes;

    @ManyToOne
    @JoinColumn(name="ancestry_id")
    private AncestryEntity ancestry;

    @ManyToOne
    @JoinColumn(name="bibliography_id")
    private BibliographyEntity bibliography;

    @ManyToOne
    @JoinColumn(name="class_id")
    private ClassEntity classEntity;

    @ElementCollection
    @CollectionTable(
            name = "character_skills",
            joinColumns = @JoinColumn(name = "character_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "skill")
    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency")
    private Map<SkillType, Proficiency> skills;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "character_talents",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "talent_id")
    )
    private List<TalentEntity> talents;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity owner;
}
