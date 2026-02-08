package com.internmatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company extends UserBase {

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String industry;

    protected Company() {}

    public Company(String fullName, String email, String companyName, String industry) {
        super(fullName, email);
        this.companyName = companyName;
        this.industry = industry;
    }

    public String getCompanyName() { return companyName; }
    public String getIndustry() { return industry; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setIndustry(String industry) { this.industry = industry; }
}
