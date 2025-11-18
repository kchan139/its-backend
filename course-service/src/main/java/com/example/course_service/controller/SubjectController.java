package com.example.course_service.controller;

import com.example.course_service.dto.request.SubjectRequestDTO;
import com.example.course_service.dto.response.SubjectResponseDTO;
import com.example.course_service.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping // CREATE
    public ResponseEntity<SubjectResponseDTO> createSubject(
            @Valid @RequestBody SubjectRequestDTO subjectRequestDTO) { // Đầu vào là RequestDTO

        SubjectResponseDTO created = subjectService.createSubject(subjectRequestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // UPDATE
    public ResponseEntity<SubjectResponseDTO> updateSubject(
            @PathVariable Long id,
            @Valid @RequestBody SubjectRequestDTO subjectRequestDTO) { // Đầu vào là RequestDTO

        SubjectResponseDTO updated = subjectService.updateSubject(id, subjectRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}") // READ (by ID)
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable Long id) {

        SubjectResponseDTO subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @GetMapping // READ (all)
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {

        List<SubjectResponseDTO> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @DeleteMapping("/{id}") // DELETE
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}