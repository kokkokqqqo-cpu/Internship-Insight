package com.internmatch.scoring;

import java.util.List;

public class ScoreCalculator {

    private static final ScoreCalculator INSTANCE = new ScoreCalculator();

    private final List<ScoreStrategy> strategies;

    private ScoreCalculator() {
        this.strategies = List.of(
                new SkillMatchStrategy(),
                new ProfileCompletenessStrategy(),
                new ExperienceStrategy()
        );
    }

    public static ScoreCalculator getInstance() {
        return INSTANCE;
    }

    public double calculate(com.internmatch.model.Student student, com.internmatch.model.Internship internship) {
        double total = 0.0;
        for (ScoreStrategy s : strategies) {
            total += s.score(student, internship);
        }
        return total;
    }
}
