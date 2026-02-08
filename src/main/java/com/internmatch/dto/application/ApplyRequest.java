package com.internmatch.dto.application;

import jakarta.validation.constraints.NotNull;

public record ApplyRequest(
        @NotNull Long studentId,
        @NotNull Long internshipId
) {}
