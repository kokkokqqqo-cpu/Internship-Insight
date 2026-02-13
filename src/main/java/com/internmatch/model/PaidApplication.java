package com.internmatch.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PAID")
public class PaidApplication extends Application {

    @Column(nullable = false)
    private Integer expectedSalary;

    protected PaidApplication() {}

    public PaidApplication(Student student, Internship internship, Integer expectedSalary) {
        super(student, internship);
        this.expectedSalary = expectedSalary;
    }

    public Integer getExpectedSalary() { return expectedSalary; }
    public void setExpectedSalary(Integer expectedSalary) { this.expectedSalary = expectedSalary; }

    @Override
    public String getKind() {
        return "PAID";
    }
}
