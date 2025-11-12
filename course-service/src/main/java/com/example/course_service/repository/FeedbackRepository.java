package com.example.course_service.repository;

import com.example.course_service.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findBySubject_SubjectId(Long subjectId);
    List<Feedback> findByStudentId(String studentId);
}
