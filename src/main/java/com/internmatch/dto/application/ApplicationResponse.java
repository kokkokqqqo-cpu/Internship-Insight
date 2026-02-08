package com.internmatch.dto.application;

import java.time.LocalDateTime;

public record ApplicationResponse(
        Long id,
        String kind,
        Long studentId,
        Long internshipId,
        Double score,
        LocalDateTime createdAt
) {}
