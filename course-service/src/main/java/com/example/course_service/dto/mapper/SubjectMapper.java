package com.example.course_service.dto.mapper;

import com.example.course_service.dto.request.SubjectRequestDTO;
import com.example.course_service.dto.response.SubjectResponseDTO;
import com.example.course_service.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    // Chuyển từ Entity sang Response DTO (Trả về cho Client)
    public SubjectResponseDTO toResponseDTO(Subject subject) {
        return SubjectResponseDTO.builder()
                .subjectId(subject.getSubjectId()) // Bao gồm ID
                .name(subject.getName())
                .description(subject.getDescription())
                .build();
    }

    // Chuyển từ Request DTO sang Entity (Nhận từ Client)
    public Subject toEntity(SubjectRequestDTO dto) {
        return Subject.builder()

                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}