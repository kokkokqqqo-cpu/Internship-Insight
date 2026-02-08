package com.internmatch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "app_kind", discriminatorType = DiscriminatorType.STRING)
public abstract class ApplicationBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id")
    private Internship internship;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false)
    private String motivation;

    protected ApplicationBase() {}

    protected ApplicationBase(Student student, Internship internship) {
        this.student = student;
        this.internship = internship;
        this.createdAt = LocalDateTime.now();
        this.score = 0.0;
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Internship getInternship() { return internship; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Double getScore() { return score; }

    public void setScore(Double score) { this.score = score; }

    public abstract String getKind();
}
