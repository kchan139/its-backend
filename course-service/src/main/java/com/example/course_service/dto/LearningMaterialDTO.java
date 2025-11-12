package com.example.course_service.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningMaterialDTO {
    private Long materialId;

    @NotBlank(message = "Title is required")
    private String title;

    private String content;
    private String type;
    private Integer duration;

    @NotNull(message = "Topic ID is required")
    private Long topicId;

    private Set<Long> tagIds;
}
