package com.example.course_service.controller;

import com.example.course_service.dto.request.FeedbackRequestDTO;
import com.example.course_service.dto.response.FeedbackResponseDTO;
import com.example.course_service.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> createFeedback( // Trả về ResponseDTO
                                                               @Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO, // Đầu vào là RequestDTO
                                                               Authentication authentication) {

        // Truyền cả DTO và Authentication sang Service
        FeedbackResponseDTO created = feedbackService.createFeedback(feedbackRequestDTO, authentication);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDTO> getFeedbackById(@PathVariable Long id) { // Trả về ResponseDTO
        FeedbackResponseDTO feedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbacksBySubjectId(@PathVariable Long subjectId) { // List ResponseDTO
        List<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksBySubjectId(subjectId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponseDTO>> getAllFeedbacks() { // List ResponseDTO
        List<FeedbackResponseDTO> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}

