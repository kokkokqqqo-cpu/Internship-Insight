package com.internmatch.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class UserBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    protected UserBase() {}

    protected UserBase(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
}
