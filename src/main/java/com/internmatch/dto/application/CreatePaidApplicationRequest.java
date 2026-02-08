package com.internmatch.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaidApplicationRequest(
        @NotNull Long studentId,
        @NotNull Long internshipId,
        @NotNull @Positive Integer expectedSalary,
        @NotBlank String motivation
) {}
