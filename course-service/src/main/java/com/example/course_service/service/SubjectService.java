package com.example.course_service.service;

import com.example.course_service.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {
    SubjectDTO createSubject(SubjectDTO subjectDTO);
    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);
    SubjectDTO getSubjectById(Long id);
    List<SubjectDTO> getAllSubjects();
    void deleteSubject(Long id);
}