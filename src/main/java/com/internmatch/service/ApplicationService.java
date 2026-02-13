package com.internmatch.service;

import com.internmatch.exception.BadRequestException;
import com.internmatch.exception.NotFoundException;
import com.internmatch.factory.ApplicationFactory;
import com.internmatch.model.Application;
import com.internmatch.model.Internship;
import com.internmatch.model.Student;
import com.internmatch.repository.ApplicationRepository;
import com.internmatch.repository.InternshipRepository;
import com.internmatch.scoring.ScoreCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentService studentService;
    private final InternshipRepository internshipRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              StudentService studentService,
                              InternshipRepository internshipRepository) {
        this.applicationRepository = applicationRepository;
        this.studentService = studentService;
        this.internshipRepository = internshipRepository;
    }

    @Transactional
    public Application applyPaid(Long studentId, Long internshipId, Integer expectedSalary) {
        return applyInternal(studentId, internshipId, expectedSalary, null);
    }

    @Transactional
    public Application applyUnpaid(Long studentId, Long internshipId, String motivation) {
        return applyInternal(studentId, internshipId, null, motivation);
    }

    private Application applyInternal(Long studentId, Long internshipId, Integer expectedSalary, String motivation) {
        Student student = studentService.getById(studentId);

        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new NotFoundException("Internship not found: " + internshipId));

        if (internship.getSeatsAvailable() == null || internship.getSeatsAvailable() < 1) {
            throw new BadRequestException("No seats available");
        }

        ApplicationFactory factory = new ApplicationFactory();
        Application app = factory.createForInternship(student, internship, expectedSalary, motivation);

        double score = ScoreCalculator.getInstance().calculate(student, internship);
        app.setScore(score);

        Application saved = applicationRepository.save(app);

        internship.setSeatsAvailable(internship.getSeatsAvailable() - 1);
        internshipRepository.save(internship);

        return saved;
    }

    public List<Application> rankByInternship(Long internshipId) {
        return applicationRepository.findByInternshipIdOrderByScoreDesc(internshipId);
    }

    public List<Application> getByStudent(Long studentId) {
        return applicationRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
    }
}
