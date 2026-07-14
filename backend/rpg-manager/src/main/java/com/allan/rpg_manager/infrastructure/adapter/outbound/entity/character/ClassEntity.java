package com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character;

import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @Column
    String description;

    @ManyToMany
    List<TalentEntity> talents;

    @Column(nullable = false,name="health_points")
    Integer healthPoints;

    @Column(nullable = false, name="primary_attribute")
    @Enumerated(EnumType.STRING)
    Attribute primaryAttribute;

    @ElementCollection
    @CollectionTable(name = "class_fixed_skills", joinColumns = @JoinColumn(name = "class_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "skill")
    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency")
    Map<SkillType, Proficiency> fixedSkills;

    @Column(nullable = false)
    Integer skillsExtra;
}
