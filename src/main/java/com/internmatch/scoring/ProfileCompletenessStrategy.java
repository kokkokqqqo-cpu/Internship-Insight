package com.internmatch.scoring;

import com.internmatch.model.Internship;
import com.internmatch.model.Student;

public class ProfileCompletenessStrategy implements ScoreStrategy {

    @Override
    public double score(Student student, Internship internship) {
        int points = 0;

        if (student.getFullName() != null && !student.getFullName().isBlank()) points += 10;
        if (student.getEmail() != null && !student.getEmail().isBlank()) points += 10;
        if (student.getAge() != null && student.getAge() > 0) points += 10;

        return points;
    }
}

