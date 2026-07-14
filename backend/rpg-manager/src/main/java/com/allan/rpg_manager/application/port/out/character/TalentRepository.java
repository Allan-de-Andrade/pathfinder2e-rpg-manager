package com.allan.rpg_manager.application.port.out.character;

import com.allan.rpg_manager.domains.characterDomain.Talent;

import java.util.List;

public interface TalentRepository {
    List<Talent> findAll();
    List<Talent> findByClassId(Long classId);
    List<Talent> findByBibliographyId(Long bibliographyId);
    List<Talent> findByAncestryId(Long ancestryId);
}
