package com.example.course_service.service;

import com.example.course_service.dto.request.SubjectRequestDTO;
import com.example.course_service.dto.response.SubjectResponseDTO;

import java.util.List;

public interface SubjectService {
    SubjectResponseDTO createSubject(SubjectRequestDTO subjectRequestDTO);
    SubjectResponseDTO updateSubject(Long id, SubjectRequestDTO subjectDTO);
    SubjectResponseDTO getSubjectById(Long id);
    List<SubjectResponseDTO> getAllSubjects();
    void deleteSubject(Long id);
}