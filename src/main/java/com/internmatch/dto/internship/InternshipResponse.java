package com.internmatch.dto.internship;

import com.internmatch.model.Internship;

import java.time.LocalDate;
import java.util.Set;

public record InternshipResponse(
        Long id,
        String title,
        Long companyId,
        String companyName,
        Internship.InternshipType type,
        Integer seatsAvailable,
        LocalDate deadline,
        Set<String> requiredSkills
) {}
