package com.internmatch.controller;

import com.internmatch.dto.application.ApplicationResponse;
import com.internmatch.dto.application.CreatePaidApplicationRequest;
import com.internmatch.dto.application.CreateUnpaidApplicationRequest;
import com.internmatch.model.Application;
import com.internmatch.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/paid")
    public ApplicationResponse applyPaid(@Valid @RequestBody CreatePaidApplicationRequest req) {
        Application a = applicationService.applyPaid(req.studentId(), req.internshipId(), req.expectedSalary());
        return toResponse(a);
    }

    @PostMapping("/unpaid")
    public ApplicationResponse applyUnpaid(@Valid @RequestBody CreateUnpaidApplicationRequest req) {
        Application a = applicationService.applyUnpaid(req.studentId(), req.internshipId(), req.motivation());
        return toResponse(a);
    }

    @GetMapping("/rank/internship/{internshipId}")
    public java.util.List<ApplicationResponse> rank(@PathVariable Long internshipId) {
        return applicationService.rankByInternship(internshipId).stream().map(this::toResponse).toList();
    }

    @GetMapping("/student/{studentId}")
    public java.util.List<ApplicationResponse> byStudent(@PathVariable Long studentId) {
        return applicationService.getByStudent(studentId).stream().map(this::toResponse).toList();
    }

    private ApplicationResponse toResponse(Application a) {
        return new ApplicationResponse(
                a.getId(),
                a.getKind(),
                a.getStudent().getId(),
                a.getInternship().getId(),
                a.getScore(),
                a.getCreatedAt()
        );
    }
}
