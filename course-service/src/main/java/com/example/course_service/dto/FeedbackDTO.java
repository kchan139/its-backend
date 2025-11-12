package com.example.course_service.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTO {
    private Long feedbackId;

    @NotBlank(message = "Feedback content is required")
    private String content;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}