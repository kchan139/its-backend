package com.example.course_service.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicRequestDTO {

    @NotBlank(message = "Topic name is required")
    private String name;

    private String description;

    private Integer difficultyLevel;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}

