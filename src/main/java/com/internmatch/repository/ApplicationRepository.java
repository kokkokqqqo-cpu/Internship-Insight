package com.internmatch.repository;

import com.internmatch.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByInternshipIdOrderByScoreDesc(Long internshipId);
    List<Application> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}
