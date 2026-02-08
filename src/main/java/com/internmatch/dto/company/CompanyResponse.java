package com.internmatch.dto.company;

public record CompanyResponse(
        Long id,
        String fullName,
        String email,
        String companyName,
        String industry
) {}
