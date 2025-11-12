package com.example.course_service.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDTO {
    private Long topicId;

    @NotBlank(message = "Topic name is required")
    private String name;

    private String description;

    private String difficultyLevel;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}

