package com.internmatch.controller;

import com.internmatch.dto.student.StudentCreateRequest;
import com.internmatch.dto.student.StudentResponse;
import com.internmatch.dto.student.StudentUpdateRequest;
import com.internmatch.model.Skill;
import com.internmatch.model.Student;
import com.internmatch.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public java.util.List<StudentResponse> getAll() {
        return studentService.getAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable Long id) {
        return toResponse(studentService.getById(id));
    }

    @PostMapping
    public StudentResponse create(@Valid @RequestBody StudentCreateRequest req) {
        Student s = studentService.create(req.fullName(), req.email(), req.age(), req.experienceMonths());
        return toResponse(s);
    }

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest req) {
        Student s = studentService.update(id, req.fullName(), req.email(), req.age(), req.experienceMonths());
        return toResponse(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }

    private StudentResponse toResponse(Student s) {
        Set<String> skills = s.getSkills().stream().map(Skill::getName).collect(Collectors.toSet());
        return new StudentResponse(s.getId(), s.getFullName(), s.getEmail(), s.getAge(), s.getExperienceMonths(), skills);
    }
}
