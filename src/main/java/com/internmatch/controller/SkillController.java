package com.internmatch.controller;

import com.internmatch.dto.skill.SkillCreateRequest;
import com.internmatch.dto.skill.SkillResponse;
import com.internmatch.model.Skill;
import com.internmatch.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public java.util.List<SkillResponse> getAll() {
        return skillService.getAll().stream().map(this::toResponse).toList();
    }

    @PostMapping
    public SkillResponse create(@Valid @RequestBody SkillCreateRequest req) {
        Skill s = skillService.create(req.name());
        return toResponse(s);
    }

    private SkillResponse toResponse(Skill s) {
        return new SkillResponse(s.getId(), s.getName());
    }
}
