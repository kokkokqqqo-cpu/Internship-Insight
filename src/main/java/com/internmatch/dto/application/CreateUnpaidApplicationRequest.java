package com.internmatch.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUnpaidApplicationRequest(
        @NotNull Long studentId,
        @NotNull Long internshipId,
        @NotBlank String motivation
) {}
