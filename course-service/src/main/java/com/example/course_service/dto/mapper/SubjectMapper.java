package com.example.course_service.dto.mapper;

import com.example.course_service.dto.SubjectDTO;
import com.example.course_service.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public SubjectDTO toDTO(Subject subject) {
        return SubjectDTO.builder()
                .subjectId(subject.getSubjectId())
                .name(subject.getName())
                .description(subject.getDescription())
                .build();
    }

    public Subject toEntity(SubjectDTO dto) {
        return Subject.builder()
                .subjectId(dto.getSubjectId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}