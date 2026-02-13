package com.internmatch.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("UNPAID")
public class UnpaidApplication extends Application {

    @Column(nullable = false)
    private String motivation;

    protected UnpaidApplication() {}

    public UnpaidApplication(Student student, Internship internship, String motivation) {
        super(student, internship);
        this.motivation = motivation;
    }

    public String getMotivation() { return motivation; }
    public void setMotivation(String motivation) { this.motivation = motivation; }

    @Override
    public String getKind() {
        return "UNPAID";
    }
}
