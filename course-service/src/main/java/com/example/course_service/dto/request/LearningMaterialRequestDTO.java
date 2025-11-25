package com.example.course_service.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningMaterialRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String content;
    private String type;
    private Integer duration;

    @NotNull(message = "Topic ID is required")
    private Long topicId;

}
