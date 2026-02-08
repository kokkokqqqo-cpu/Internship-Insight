package com.internmatch.service;

import com.internmatch.builder.InternshipBuilder;
import com.internmatch.exception.BadRequestException;
import com.internmatch.exception.NotFoundException;
import com.internmatch.model.Company;
import com.internmatch.model.Internship;
import com.internmatch.model.Skill;
import com.internmatch.repository.InternshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyService companyService;
    private final SkillService skillService;

    public InternshipService(InternshipRepository internshipRepository,
                             CompanyService companyService,
                             SkillService skillService) {
        this.internshipRepository = internshipRepository;
        this.companyService = companyService;
        this.skillService = skillService;
    }

    public List<Internship> getAll() {
        return internshipRepository.findAll();
    }

    public Internship getById(Long id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Internship not found: " + id));
    }

    @Transactional
    public Internship create(String title,
                             Long companyId,
                             Internship.InternshipType type,
                             Integer seatsAvailable,
                             LocalDate deadline,
                             Set<String> requiredSkillNames) {

        if (seatsAvailable == null || seatsAvailable < 1) {
            throw new BadRequestException("seatsAvailable must be >= 1");
        }

        Company company = companyService.getById(companyId);

        Set<Skill> skills = new HashSet<>();
        for (String name : requiredSkillNames) {
            skills.add(skillService.create(name));
        }

        InternshipBuilder b = new InternshipBuilder()
                .title(title)
                .company(company)
                .type(type)
                .seatsAvailable(seatsAvailable)
                .deadline(deadline)
                .requiredSkills(skills);

        return internshipRepository.save(b.build());
    }

    @Transactional
    public Internship update(Long id,
                             String title,
                             Internship.InternshipType type,
                             Integer seatsAvailable,
                             LocalDate deadline,
                             Set<String> requiredSkillNames) {

        Internship i = getById(id);

        if (seatsAvailable == null || seatsAvailable < 1) {
            throw new BadRequestException("seatsAvailable must be >= 1");
        }

        Set<Skill> skills = new HashSet<>();
        for (String name : requiredSkillNames) {
            skills.add(skillService.create(name));
        }

        i.setTitle(title);
        i.setType(type);
        i.setSeatsAvailable(seatsAvailable);
        i.setDeadline(deadline);
        i.setRequiredSkills(skills);

        return internshipRepository.save(i);
    }

    @Transactional
    public void delete(Long id) {
        Internship i = getById(id);
        internshipRepository.delete(i);
    }
}
