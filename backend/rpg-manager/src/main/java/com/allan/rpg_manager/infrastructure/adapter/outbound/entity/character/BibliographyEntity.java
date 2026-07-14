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
@Table(name = "bibliographies")
@Getter
@Setter
@NoArgsConstructor
public class BibliographyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String name;

    @Column
    String description;

    @ElementCollection
    @CollectionTable(name = "bibliography_skills", joinColumns = @JoinColumn(name = "bibliography_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "skill")
    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency")
    Map<SkillType, Proficiency> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<Attribute> attributesFixedBuffer;

    @Enumerated(EnumType.STRING)
    Attribute attributeFreeBuffer;

    @ManyToOne
    TalentEntity talent;
}
