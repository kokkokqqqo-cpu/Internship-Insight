package com.internmatch.service;

import com.internmatch.exception.NotFoundException;
import com.internmatch.factory.UserFactory;
import com.internmatch.model.Skill;
import com.internmatch.model.Student;
import com.internmatch.model.UserBase;
import com.internmatch.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SkillService skillService;

    public StudentService(StudentRepository studentRepository, SkillService skillService) {
        this.studentRepository = studentRepository;
        this.skillService = skillService;
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));
    }

    @Transactional
    public Student create(String fullName, String email, Integer age, Integer experienceMonths) {
        UserFactory f = new UserFactory();
        UserBase u = f.create(UserFactory.UserRole.STUDENT, fullName, email, age, experienceMonths, null, null);
        return studentRepository.save((Student) u);
    }

    @Transactional
    public Student update(Long id, String fullName, String email, Integer age, Integer experienceMonths) {
        Student s = getById(id);
        s.setFullName(fullName);
        s.setEmail(email);
        s.setAge(age);
        s.setExperienceMonths(experienceMonths);
        return studentRepository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        Student s = getById(id);
        studentRepository.delete(s);
    }

    @Transactional
    public Student setSkills(Long studentId, Set<String> skillNames) {
        Student s = getById(studentId);
        Set<Skill> skills = new HashSet<>();
        for (String name : skillNames) {
            Skill sk = skillService.create(name);
            skills.add(sk);
        }
        s.setSkills(skills);
        return studentRepository.save(s);
    }
}
