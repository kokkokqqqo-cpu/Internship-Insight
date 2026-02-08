package com.internmatch.controller;

import com.internmatch.dto.company.CompanyCreateRequest;
import com.internmatch.dto.company.CompanyResponse;
import com.internmatch.dto.company.CompanyUpdateRequest;
import com.internmatch.model.Company;
import com.internmatch.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public java.util.List<CompanyResponse> getAll() {
        return companyService.getAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public CompanyResponse getById(@PathVariable Long id) {
        return toResponse(companyService.getById(id));
    }

    @PostMapping
    public CompanyResponse create(@Valid @RequestBody CompanyCreateRequest req) {
        Company c = companyService.create(req.fullName(), req.email(), req.companyName(), req.industry());
        return toResponse(c);
    }

    @PutMapping("/{id}")
    public CompanyResponse update(@PathVariable Long id, @Valid @RequestBody CompanyUpdateRequest req) {
        Company c = companyService.update(id, req.fullName(), req.email(), req.companyName(), req.industry());
        return toResponse(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        companyService.delete(id);
    }

    private CompanyResponse toResponse(Company c) {
        return new CompanyResponse(c.getId(), c.getFullName(), c.getEmail(), c.getCompanyName(), c.getIndustry());
    }
}
