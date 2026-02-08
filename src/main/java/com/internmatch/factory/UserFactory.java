package com.internmatch.factory;

import com.internmatch.model.Company;
import com.internmatch.model.Student;
import com.internmatch.model.UserBase;

public class UserFactory {

    public enum UserRole {
        STUDENT,
        COMPANY
    }

    public UserBase create(UserRole role,
                           String fullName,
                           String email,
                           Integer age,
                           Integer experienceMonths,
                           String companyName,
                           String industry) {

        if (role == null) {
            throw new IllegalArgumentException("role is null");
        }

        return switch (role) {
            case STUDENT -> new Student(fullName, email, age, experienceMonths);
            case COMPANY -> new Company(fullName, email, companyName, industry);
        };
    }
}
