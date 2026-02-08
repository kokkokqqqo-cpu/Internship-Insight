package com.internmatch.scoring;

import com.internmatch.model.Internship;
import com.internmatch.model.Skill;
import com.internmatch.model.Student;

import java.util.Set;
import java.util.stream.Collectors;

public class SkillMatchStrategy implements ScoreStrategy {

    @Override
    public double score(Student student, Internship internship) {
        if (student.getSkills() == null || internship.getRequiredSkills() == null) {
            return 0.0;
        }

        Set<Long> studentSkillIds = student.getSkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toSet());

        long matched = internship.getRequiredSkills().stream()
                .filter(s -> s.getId() != null && studentSkillIds.contains(s.getId()))
                .count();

        long required = internship.getRequiredSkills().size();
        if (required == 0) {
            return 20.0;
        }

        double ratio = (double) matched / (double) required;
        return 60.0 * ratio;
    }
}
