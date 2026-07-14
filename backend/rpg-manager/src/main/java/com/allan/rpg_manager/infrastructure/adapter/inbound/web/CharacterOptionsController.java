package com.allan.rpg_manager.infrastructure.adapter.inbound.web;

import com.allan.rpg_manager.application.dtos.character.AncestryResponse;
import com.allan.rpg_manager.application.dtos.character.BibliographyResponse;
import com.allan.rpg_manager.application.dtos.character.ClassResponse;
import com.allan.rpg_manager.application.dtos.character.TalentResponse;
import com.allan.rpg_manager.application.port.in.character.CharacterOptionsUseCase;
import com.allan.rpg_manager.domains.characterDomain.Talent;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.AncestryMapper;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.BibliographyMapper;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.ClassMapper;
import com.allan.rpg_manager.infrastructure.adapter.outbound.persistence.mapper.TalentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class CharacterOptionsController {

    private final CharacterOptionsUseCase optionsUseCase;
    private final TalentMapper talentMapper;
    private final ClassMapper classMapper;
    private final AncestryMapper ancestryMapper;
    private final BibliographyMapper bibliographyMapper;
    public CharacterOptionsController(CharacterOptionsUseCase optionsUseCase, TalentMapper talentMapper, ClassMapper classMapper, AncestryMapper ancestryMapper, BibliographyMapper bibliographyMapper) {
        this.optionsUseCase = optionsUseCase;
        this.talentMapper = talentMapper;
        this.classMapper = classMapper;
        this.ancestryMapper = ancestryMapper;
        this.bibliographyMapper = bibliographyMapper;
    }

    @GetMapping("/classes")
    public ResponseEntity<List<ClassResponse>> getAllClasses() {
        List<ClassResponse> classResponses = optionsUseCase.findCharacterClasses().stream().
                map(classMapper::toResponse).toList();
        return ResponseEntity.ok(classResponses);
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<ClassResponse> getClassById(@PathVariable Long id) {
        ClassResponse classResponse = classMapper.toResponse(optionsUseCase.findClassById(id));
        return ResponseEntity.ok(classResponse);
    }

    @GetMapping("/bibliographies")
    public ResponseEntity<List<BibliographyResponse>> getAllBibliographies() {
        List<BibliographyResponse> responses = optionsUseCase.findBibliographies().stream().
                map(bibliographyMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/bibliographies/{id}")
    public ResponseEntity<BibliographyResponse> getBibliographyById(@PathVariable Long id) {
        BibliographyResponse response = bibliographyMapper.toResponse(optionsUseCase.findBibiliographyById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ancestries")
    public ResponseEntity<List<AncestryResponse>> getAllAncestries() {
        List<AncestryResponse> response = optionsUseCase.findAncestries().stream().
                map(ancestryMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ancestries/{id}")
    public ResponseEntity<AncestryResponse> getAncestryById(@PathVariable Long id) {
        AncestryResponse response = ancestryMapper.toResponse(optionsUseCase.findAncestryById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/talents")
    public ResponseEntity<Page<Talent>> getAllTalents(
            @RequestParam(defaultValue = "0", required = false, value = "page") int page,
            @RequestParam(defaultValue = "5", required = false, value = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(optionsUseCase.findTalents(pageable));
    }

    @GetMapping("/talents/class/{classId}")
    public ResponseEntity<List<TalentResponse>> getTalentsByClass(
            @PathVariable Long classId,
            @RequestParam(defaultValue = "0", required = false, value = "page") int page,
            @RequestParam(defaultValue = "5", required = false, value = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<TalentResponse> responses = optionsUseCase.findTalentsByClass(classId,pageable).
                stream().map(talentMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/talents/bibliography/{bibliographyId}")
    public ResponseEntity<List<TalentResponse>> getTalentsByBibliography(
            @PathVariable Long bibliographyId,
            @RequestParam(defaultValue = "0", required = false, value = "page") int page,
            @RequestParam(defaultValue = "5", required = false, value = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<TalentResponse> responses = optionsUseCase.findTalentsByBibliography(bibliographyId,pageable).
                stream().map(talentMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/talents/ancestry/{ancestryId}")
    public ResponseEntity<List<TalentResponse>> getTalentsByAncestry(
            @PathVariable Long ancestryId,
            @RequestParam(defaultValue = "0", required = false, value = "page") int page,
            @RequestParam(defaultValue = "5", required = false, value = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<TalentResponse> responses = optionsUseCase.findTalentsByBibliography(ancestryId,pageable).
                stream().map(talentMapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }
}