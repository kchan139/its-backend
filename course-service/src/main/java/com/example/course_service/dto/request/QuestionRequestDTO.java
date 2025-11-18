package com.example.course_service.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequestDTO {
    @NotBlank(message = "Question text is required")
    private String text;

    private String type;
    private String correctAnswer;
    private String hintContent;

    @NotNull(message = "Topic ID is required")
    private Long topicId;
}
