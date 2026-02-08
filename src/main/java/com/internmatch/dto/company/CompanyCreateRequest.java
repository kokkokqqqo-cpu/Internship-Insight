package com.internmatch.dto.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyCreateRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String companyName,
        @NotBlank String industry
) {}
