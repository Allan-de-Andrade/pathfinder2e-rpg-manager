package com.allan.rpg_manager.application.services;

import com.allan.rpg_manager.application.port.in.character.CharacterOptionsUseCase;
import com.allan.rpg_manager.application.port.out.character.AncestryRepository;
import com.allan.rpg_manager.application.port.out.character.BibliographyRepository;
import com.allan.rpg_manager.application.port.out.character.CharacterClassRepository;
import com.allan.rpg_manager.application.port.out.character.TalentRepository;
import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterOptionsService implements CharacterOptionsUseCase {
    private final AncestryRepository ancestryRepository;
    private final CharacterClassRepository classRepository;
    private final BibliographyRepository bibliographyRepository;
    private final TalentRepository talentRepository;

    public CharacterOptionsService(
            AncestryRepository ancestryRepository,
            CharacterClassRepository classRepository,
            BibliographyRepository bibliographyRepository,
            TalentRepository talentRepository
    ) {
        this.ancestryRepository = ancestryRepository;
        this.classRepository = classRepository;
        this.bibliographyRepository = bibliographyRepository;
        this.talentRepository = talentRepository;
    }

    @Override
    public List<CharacterClass> findCharacterClasses() {
        return classRepository.findAll();
    }

    @Override
    public CharacterClass findClassById(Long id) {
        Optional<CharacterClass> characterClass = classRepository.findById(id);
        if (characterClass.isEmpty())
            throw new IllegalArgumentException("Can't find this Ancestry with this id!");
        return characterClass.get();
    }

    @Override
    public List<Bibliography> findBibliographies() {
        return bibliographyRepository.findAll();
    }

    @Override
    public Bibliography findBibiliographyById(Long id) {
        Optional<Bibliography> bibliography = bibliographyRepository.findById(id);
        if(bibliography.isEmpty())
            throw new IllegalArgumentException("Can't find this Bibliography with this id!");
        return bibliography.get();
    }

    @Override
    public List<Ancestry> findAncestries() {
        return ancestryRepository.findAll();
    }

    @Override
    public Ancestry findAncestryById(Long id) {
        Optional<Ancestry> ancestry = ancestryRepository.findById(id);
        if (ancestry.isEmpty())
            throw new IllegalArgumentException("Can't find this Ancestry with this id!");
        return ancestry.get();
    }
    @Override

    public Page<Talent> findTalents(Pageable pageable) {
        List<Talent> talents = talentRepository.findAll();
        return new PageImpl<>(talents,pageable,talents.size());
    }

    @Override
    public Page<Talent> findTalentsByClass(Long classId,Pageable pageable) {
        List<Talent> talents = talentRepository.findByClassId(classId);
        return new PageImpl<>(talents,pageable,talents.size());
    }

    @Override
    public Page<Talent> findTalentsByBibliography(Long bibliographyId,Pageable pageable) {
        List<Talent> talents = talentRepository.findByBibliographyId(bibliographyId);
        return new PageImpl<>(talents,pageable,talents.size());
    }

    @Override
    public Page<Talent> findTalentsByAncestry(Long ancestryId,Pageable pageable) {
        List<Talent> talents = talentRepository.findByAncestryId(ancestryId);
        return new PageImpl<>(talents,pageable,talents.size());
    }
}
