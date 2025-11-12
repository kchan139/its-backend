package com.example.course_service.service;

import com.example.course_service.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackDTO createFeedback(FeedbackDTO feedbackDTO, String studentId);
    FeedbackDTO getFeedbackById(Long id);
    List<FeedbackDTO> getFeedbacksBySubjectId(Long subjectId);
    List<FeedbackDTO> getAllFeedbacks();
    void deleteFeedback(Long id);
}
