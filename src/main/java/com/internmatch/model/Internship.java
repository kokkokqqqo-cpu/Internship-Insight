package com.internmatch.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "internships")
public class Internship {

    public enum InternshipType {
        PAID,
        UNPAID
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InternshipType type;

    @Column(nullable = false)
    private Integer seatsAvailable;

    @Column(nullable = false)
    private LocalDate deadline;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "internship_required_skills",
            joinColumns = @JoinColumn(name = "internship_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    public Internship() {}

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Company getCompany() { return company; }
    public InternshipType getType() { return type; }
    public Integer getSeatsAvailable() { return seatsAvailable; }
    public LocalDate getDeadline() { return deadline; }
    public Set<Skill> getRequiredSkills() { return requiredSkills; }

    public void setTitle(String title) { this.title = title; }
    public void setCompany(Company company) { this.company = company; }
    public void setType(InternshipType type) { this.type = type; }
    public void setSeatsAvailable(Integer seatsAvailable) { this.seatsAvailable = seatsAvailable; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setRequiredSkills(Set<Skill> requiredSkills) { this.requiredSkills = requiredSkills; }
}
