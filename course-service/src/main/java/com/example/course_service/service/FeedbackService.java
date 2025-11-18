package com.example.course_service.service;

import com.example.course_service.dto.request.FeedbackRequestDTO;
import com.example.course_service.dto.response.FeedbackResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FeedbackService {
    FeedbackResponseDTO createFeedback(FeedbackRequestDTO feedbackRequestDTO, Authentication authentication);

    FeedbackResponseDTO getFeedbackById(Long id);

    List<FeedbackResponseDTO> getFeedbacksBySubjectId(Long subjectId);

    List<FeedbackResponseDTO> getAllFeedbacks();

    void deleteFeedback(Long id);
}
