package com.internmatch.factory;

import com.internmatch.model.*;

public class ApplicationFactory {

    public ApplicationBase createForInternship(Student student,
                                               Internship internship,
                                               Integer expectedSalary,
                                               String motivation) {

        if (internship == null || internship.getType() == null) {
            throw new IllegalArgumentException("internship type missing");
        }

        return switch (internship.getType()) {
            case PAID -> new PaidApplication(student, internship, expectedSalary == null ? 0 : expectedSalary);
            case UNPAID -> new UnpaidApplication(student, internship, motivation == null ? "" : motivation);
        };
    }
}
