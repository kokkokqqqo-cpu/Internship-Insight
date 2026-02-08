package com.internmatch.scoring;

import com.internmatch.model.Internship;
import com.internmatch.model.Student;

public class ExperienceStrategy implements ScoreStrategy {

    @Override
    public double score(Student student, Internship internship) {
        int months = student.getExperienceMonths() == null ? 0 : student.getExperienceMonths();

        if (months >= 24) return 20.0;
        if (months >= 12) return 15.0;
        if (months >= 6) return 10.0;
        if (months >= 1) return 5.0;
        return 0.0;
    }
}
