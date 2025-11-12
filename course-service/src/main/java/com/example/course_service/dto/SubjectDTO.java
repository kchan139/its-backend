package com.example.course_service.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {
    private Long subjectId;

    @NotBlank(message = "Subject name is required")
    private String name;

    private String description;
}

