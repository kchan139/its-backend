package com.example.course_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackResponseDTO {

    // ID của feedback do hệ thống quản lý
    private Long feedbackId;

    private String content;

    // ID của Subject mà nó thuộc về
    private Long subjectId;

    private Long studentId;
}
