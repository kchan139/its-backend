package com.example.course_service.dto.mapper;

import com.example.course_service.dto.request.FeedbackRequestDTO;
import com.example.course_service.dto.response.FeedbackResponseDTO;
import com.example.course_service.entity.Feedback;
import com.example.course_service.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackResponseDTO toResponseDTO(Feedback feedback) {

        return FeedbackResponseDTO.builder()
                .feedbackId(feedback.getFeedbackId())
                .content(feedback.getContent())
                // Lấy subjectId từ Entity
                .subjectId(feedback.getSubject() != null ? feedback.getSubject().getSubjectId() : null)
                // Giả định Entity có trường studentId
                .studentId(feedback.getStudentId())
                .build();
    }

    // Ánh xạ Request DTO -> Entity (Nhận từ Client)
    public Feedback toEntity(FeedbackRequestDTO dto,Long studentId) {

        Subject subject = Subject.builder().subjectId(dto.getSubjectId()).build();

        return Feedback.builder()
                // Bỏ feedbackId khi tạo mới
                .content(dto.getContent())
                .subject(subject) // Gán đối tượng Subject với ID
                .studentId(studentId)
                .build();
    }

    // Thêm phương thức để cập nhật Entity từ Request DTO (cho trường hợp PUT/PATCH)
    public void updateEntityFromDTO(Feedback feedback, FeedbackRequestDTO dto) {
        feedback.setContent(dto.getContent());
    }
}
