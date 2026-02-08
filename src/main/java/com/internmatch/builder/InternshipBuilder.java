package com.internmatch.builder;

import com.internmatch.model.Company;
import com.internmatch.model.Internship;
import com.internmatch.model.Skill;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class InternshipBuilder {
    private String title;
    private Company company;
    private Internship.InternshipType type;
    private Integer seatsAvailable;
    private LocalDate deadline;
    private Set<Skill> requiredSkills = new HashSet<>();

    public InternshipBuilder title(String title) {
        this.title = title;
        return this;
    }

    public InternshipBuilder company(Company company) {
        this.company = company;
        return this;
    }

    public InternshipBuilder type(Internship.InternshipType type) {
        this.type = type;
        return this;
    }

    public InternshipBuilder seatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
        return this;
    }

    public InternshipBuilder deadline(LocalDate deadline) {
        this.deadline = deadline;
        return this;
    }

    public InternshipBuilder requiredSkills(Set<Skill> skills) {
        this.requiredSkills = skills;
        return this;
    }

    public Internship build() {
        Internship i = new Internship();
        i.setTitle(title);
        i.setCompany(company);
        i.setType(type);
        i.setSeatsAvailable(seatsAvailable);
        i.setDeadline(deadline);
        i.setRequiredSkills(requiredSkills);
        return i;
    }
}
