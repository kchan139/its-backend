package com.example.course_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LearningMaterialResponseDTO {
    private Long materialId;
    private String title;
    private String content;
    private String type;
    private Integer duration;
    private Long topicId;

    // File metadata
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private String contentType;

}
