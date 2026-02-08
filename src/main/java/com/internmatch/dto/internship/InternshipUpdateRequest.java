package com.internmatch.dto.internship;

import com.internmatch.model.Internship;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record InternshipUpdateRequest(
        @NotBlank String title,
        @NotNull Internship.InternshipType type,
        @NotNull @Min(1) Integer seatsAvailable,
        @NotNull LocalDate deadline,
        @NotNull Set<String> requiredSkills
) {}
