package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.jpa;

import com.allan.rpg_manager.infrastructure.adapter.outbound.entity.character.TalentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TalentRepositoryJPA extends JpaRepository<TalentEntity,Long> {
    @Query("select talent from ClassEntity characterClass join characterClass.talents talent where characterClass.id = :classId")
    List<TalentEntity> findAllByClassId(@Param("classId") Long classId);

    @Query("select bibliography.talent from BibliographyEntity bibliography where bibliography.id = :bibliographyId and bibliography.talent is not null")
    List<TalentEntity> findAllByBibliographyId(@Param("bibliographyId") Long bibliographyId);

    @Query("select talent from AncestryEntity ancestry join ancestry.talents talent where ancestry.id = :ancestryId")
    List<TalentEntity> findAllByAncestryId(@Param("ancestryId") Long ancestryId);
}
