package com.internmatch.scoring;

import com.internmatch.model.Internship;
import com.internmatch.model.Student;

public interface ScoreStrategy {
    double score(Student student, Internship internship);
}
