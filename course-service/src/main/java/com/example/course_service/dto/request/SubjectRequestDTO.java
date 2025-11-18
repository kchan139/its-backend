package com.example.course_service.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectRequestDTO {
    @NotBlank(message = "Subject name is required")
    private String name;

    private String description;
}