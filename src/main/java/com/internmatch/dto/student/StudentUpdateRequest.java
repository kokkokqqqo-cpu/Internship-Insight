package com.internmatch.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentUpdateRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotNull @Min(1) Integer age,
        @NotNull @Min(0) Integer experienceMonths
) {}
