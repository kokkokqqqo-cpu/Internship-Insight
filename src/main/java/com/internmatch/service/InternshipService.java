package com.internmatch.service;

import com.internmatch.builder.InternshipBuilder;
import com.internmatch.cache.SimpleCache;
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

    private final SimpleCache cache = SimpleCache.getInstance();

    private static final String ALL_INTERNSHIPS_KEY = "allInternships";

    public InternshipService(InternshipRepository internshipRepository,
                             CompanyService companyService,
                             SkillService skillService) {
        this.internshipRepository = internshipRepository;
        this.companyService = companyService;
        this.skillService = skillService;
    }

    public List<Internship> getAll() {

        if (cache.contains(ALL_INTERNSHIPS_KEY)) {
            System.out.println("Returning from CACHE");
            return cache.get(ALL_INTERNSHIPS_KEY);
        }

        System.out.println("Returning from DATABASE");

        List<Internship> internships = cache.getOrCompute(ALL_INTERNSHIPS_KEY, internshipRepository::findAll);

        return internships;
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
            skills.add(skillService.findOrCreate(name));
        }

        InternshipBuilder builder = new InternshipBuilder()
                .title(title)
                .company(company)
                .type(type)
                .seatsAvailable(seatsAvailable)
                .deadline(deadline)
                .requiredSkills(skills);

        Internship saved = internshipRepository.save(builder.build());

        cache.clear();

        return saved;
    }

    @Transactional
    public Internship update(Long id,
                             String title,
                             Internship.InternshipType type,
                             Integer seatsAvailable,
                             LocalDate deadline,
                             Set<String> requiredSkillNames) {

        Internship internship = getById(id);

        if (seatsAvailable == null || seatsAvailable < 1) {
            throw new BadRequestException("seatsAvailable must be >= 1");
        }

        Set<Skill> skills = new HashSet<>();
        for (String name : requiredSkillNames) {
            skills.add(skillService.findOrCreate(name));
        }

        internship.setTitle(title);
        internship.setType(type);
        internship.setSeatsAvailable(seatsAvailable);
        internship.setDeadline(deadline);
        internship.setRequiredSkills(skills);

        Internship updated = internshipRepository.save(internship);

        cache.clear();

        return updated;
    }

    @Transactional
    public void delete(Long id) {
        Internship internship = getById(id);
        internshipRepository.delete(internship);

        cache.clear();
    }
}