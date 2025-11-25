package com.example.course_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectResponseDTO {

    // ID của môn học (Key chính, do hệ thống quản lý)
    private Long subjectId;

    // Tên môn học
    private String name;

    // Mô tả môn học
    private String description;

}