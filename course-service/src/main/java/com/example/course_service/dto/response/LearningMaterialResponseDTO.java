package com.example.course_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearningMaterialResponseDTO {

    // ID do hệ thống quản lý
    private Long materialId;

    private String title;
    private String content;
    private String type;
    private Integer duration;

    // Khóa ngoại của Topic mà nó thuộc về
    private Long topicId;

}
