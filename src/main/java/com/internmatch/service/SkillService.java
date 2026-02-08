package com.internmatch.service;

import com.internmatch.exception.BadRequestException;
import com.internmatch.exception.NotFoundException;
import com.internmatch.model.Skill;
import com.internmatch.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    public Skill getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill not found: " + id));
    }

    @Transactional
    public Skill create(String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Skill name is empty");
        }
        skillRepository.findByNameIgnoreCase(name).ifPresent(s -> {
            throw new BadRequestException("Skill exists: " + name);
        });
        return skillRepository.save(new Skill(name.trim()));
    }
}
