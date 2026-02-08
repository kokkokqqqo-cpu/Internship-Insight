package com.internmatch.dto.skill;

import jakarta.validation.constraints.NotBlank;

public record SkillCreateRequest(@NotBlank String name) {}
