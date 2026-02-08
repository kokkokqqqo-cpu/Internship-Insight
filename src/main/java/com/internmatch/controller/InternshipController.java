package com.internmatch.controller;

import com.internmatch.dto.internship.InternshipCreateRequest;
import com.internmatch.dto.internship.InternshipResponse;
import com.internmatch.dto.internship.InternshipUpdateRequest;
import com.internmatch.model.Internship;
import com.internmatch.model.Skill;
import com.internmatch.service.InternshipService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    private final InternshipService internshipService;

    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @GetMapping
    public java.util.List<InternshipResponse> getAll() {
        return internshipService.getAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public InternshipResponse getById(@PathVariable Long id) {
        return toResponse(internshipService.getById(id));
    }

    @PostMapping
    public InternshipResponse create(@Valid @RequestBody InternshipCreateRequest req) {
        Internship i = internshipService.create(
                req.title(),
                req.companyId(),
                req.type(),
                req.seatsAvailable(),
                req.deadline(),
                req.requiredSkills()
        );
        return toResponse(i);
    }

    @PutMapping("/{id}")
    public InternshipResponse update(@PathVariable Long id, @Valid @RequestBody InternshipUpdateRequest req) {
        Internship i = internshipService.update(
                id,
                req.title(),
                req.type(),
                req.seatsAvailable(),
                req.deadline(),
                req.requiredSkills()
        );
        return toResponse(i);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        internshipService.delete(id);
    }

    private InternshipResponse toResponse(Internship i) {
        Set<String> skills = i.getRequiredSkills().stream().map(Skill::getName).collect(Collectors.toSet());
        return new InternshipResponse(
                i.getId(),
                i.getTitle(),
                i.getCompany().getId(),
                i.getCompany().getCompanyName(),
                i.getType(),
                i.getSeatsAvailable(),
                i.getDeadline(),
                skills
        );
    }
}
