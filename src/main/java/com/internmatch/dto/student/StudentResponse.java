package com.internmatch.dto.student;

import java.util.Set;

public record StudentResponse(
        Long id,
        String fullName,
        String email,
        Integer age,
        Integer experienceMonths,
        Set<String> skills
) {}
