package com.allan.rpg_manager.application.port.in.character;

import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Ancestry;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.Bibliography;
import com.allan.rpg_manager.domains.characterDomain.valueObjects.CharacterClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CharacterOptionsUseCase {
    List<CharacterClass> findCharacterClasses();
    CharacterClass findClassById(Long id);
    List<Bibliography> findBibliographies();
    Bibliography findBibiliographyById(Long id);
    List<Ancestry> findAncestries();
    Ancestry findAncestryById(Long id);
    Page<Talent> findTalents(Pageable pageable);
    Page<Talent> findTalentsByClass(Long classId,Pageable pageable);
    Page<Talent> findTalentsByBibliography(Long bibliographyId,Pageable pageable);
    Page<Talent> findTalentsByAncestry(Long ancestryId,Pageable pageable);
}
