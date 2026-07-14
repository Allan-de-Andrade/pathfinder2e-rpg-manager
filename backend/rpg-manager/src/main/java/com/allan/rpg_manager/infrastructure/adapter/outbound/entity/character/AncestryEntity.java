package com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character;

import com.allan.rpg_manager.domains.characterDomain.enums.AncestrySize;
import com.allan.rpg_manager.domains.characterDomain.enums.Attribute;
import com.allan.rpg_manager.domains.characterDomain.enums.Language;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Heritage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name="ancestry")
@Getter
@Setter
@NoArgsConstructor
public class AncestryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    AncestrySize size;

    @Column(nullable = false)
    int speed;

    @Column(nullable = false)
    int healthPoints;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<Language> languagesDefault;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<Language> languagesOptional;

    @ManyToMany
    List<TalentEntity> talents;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<Attribute> attributesBonus;

    @Transient
    List<Heritage> heritages;

    @Enumerated(EnumType.STRING)
    Attribute attributeDebuffer;
}
