package com.example.course_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicResponseDTO {

    // ID do hệ thống quản lý
    private Long topicId;

    private String name;
    private String description;
    private Integer difficultyLevel;

    // Khóa ngoại của Subject mà nó thuộc về
    private Long subjectId;

}
