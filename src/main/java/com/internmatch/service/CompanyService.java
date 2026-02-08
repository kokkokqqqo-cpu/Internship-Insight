package com.internmatch.service;

import com.internmatch.exception.NotFoundException;
import com.internmatch.factory.UserFactory;
import com.internmatch.model.Company;
import com.internmatch.model.UserBase;
import com.internmatch.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found: " + id));
    }

    @Transactional
    public Company create(String fullName, String email, String companyName, String industry) {
        UserFactory f = new UserFactory();
        UserBase u = f.create(UserFactory.UserRole.COMPANY, fullName, email, null, null, companyName, industry);
        return companyRepository.save((Company) u);
    }

    @Transactional
    public Company update(Long id, String fullName, String email, String companyName, String industry) {
        Company c = getById(id);
        c.setFullName(fullName);
        c.setEmail(email);
        c.setCompanyName(companyName);
        c.setIndustry(industry);
        return companyRepository.save(c);
    }

    @Transactional
    public void delete(Long id) {
        Company c = getById(id);
        companyRepository.delete(c);
    }
}
