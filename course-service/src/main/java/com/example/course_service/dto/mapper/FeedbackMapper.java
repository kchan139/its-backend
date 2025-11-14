package com.example.course_service.dto.mapper;

import com.example.course_service.dto.FeedbackDTO;
import com.example.course_service.entity.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackDTO toDTO(Feedback feedback) {
        return FeedbackDTO.builder()
                .feedbackId(feedback.getFeedbackId())
                .content(feedback.getContent())
                .subjectId(feedback.getSubject() != null ? feedback.getSubject().getSubjectId() : null)
                .build();
    }

    public Feedback toEntity(FeedbackDTO dto) {
        return Feedback.builder()
                .feedbackId(dto.getFeedbackId())
                .content(dto.getContent())
                .build();
    }
}
