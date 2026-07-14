package com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper;

import com.allan.rpg_manager.application.dtos.character.SkillResponse;
import com.allan.rpg_manager.domains.characterDomain.Skill;
import com.allan.rpg_manager.domains.characterDomain.enums.Proficiency;
import com.allan.rpg_manager.domains.characterDomain.enums.SkillType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SkillMapper {

    public Map<SkillType, Proficiency> toEntitySkills(Map<SkillType, Skill> skills) {
        if (skills == null) {
            return Collections.emptyMap();
        }

        return skills.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() == null ? Proficiency.UNTRAINED : entry.getValue().getProficiency()
                ));
    }

    public Map<SkillType, Skill> toDomainSkills(Map<SkillType, Proficiency> skills) {
        Map<SkillType, Skill> domainSkills = new HashMap<>();

        for (SkillType skillType : SkillType.values()) {
            domainSkills.put(skillType, new Skill(skillType, Proficiency.UNTRAINED));
        }

        if (skills != null) {
            skills.forEach((skillType, proficiency) ->
                    domainSkills.put(skillType, new Skill(skillType, proficiency == null ? Proficiency.UNTRAINED : proficiency))
            );
        }

        return domainSkills;
    }

    public List<SkillResponse> domainsToResponses(List<Skill> skills) {
        if (skills == null) {
            return List.of();
        }
        return skills.stream()
                .map(skill -> new SkillResponse(skill.getType(), skill.getProficiency()))
                .toList();
    }
    public Map<SkillType, Proficiency> toSkillMap(java.util.List<Skill> skills) {
        if (skills == null) {
            return Collections.emptyMap();
        }

        return skills.stream()
                .collect(Collectors.toMap(
                        Skill::getType,
                        skill -> skill.getProficiency() == null ? Proficiency.UNTRAINED : skill.getProficiency()
                ));
    }

    public java.util.List<Skill> toSkillList(Map<SkillType, Proficiency> skills) {
        if (skills == null) {
            return Collections.emptyList();
        }

        return skills.entrySet().stream()
                .map(entry -> new Skill(entry.getKey(), entry.getValue() == null ? Proficiency.UNTRAINED : entry.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
