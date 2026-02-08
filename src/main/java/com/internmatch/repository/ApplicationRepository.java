package com.internmatch.repository;

import com.internmatch.model.ApplicationBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationBase, Long> {
    List<ApplicationBase> findByInternshipIdOrderByScoreDesc(Long internshipId);
    List<ApplicationBase> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}
