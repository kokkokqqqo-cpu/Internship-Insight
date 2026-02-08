package com.internmatch.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends UserBase {

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Integer experienceMonths;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_skills",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    protected Student() {}

    public Student(String fullName, String email, Integer age, Integer experienceMonths) {
        super(fullName, email);
        this.age = age;
        this.experienceMonths = experienceMonths;
    }

    public Integer getAge() { return age; }
    public Integer getExperienceMonths() { return experienceMonths; }
    public Set<Skill> getSkills() { return skills; }

    public void setAge(Integer age) { this.age = age; }
    public void setExperienceMonths(Integer experienceMonths) { this.experienceMonths = experienceMonths; }
    public void setSkills(Set<Skill> skills) { this.skills = skills; }
}
