package com.example.course_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionResponseDTO {

    // ID do hệ thống quản lý
    private Long questionId;

    private String text;
    private String type;
    private String correctAnswer;
    private String hintContent;

    // Khóa ngoại của Topic mà nó thuộc về
    private Long topicId;
}