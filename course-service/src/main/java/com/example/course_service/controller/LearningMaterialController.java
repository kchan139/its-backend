package com.example.course_service.controller;

import com.example.course_service.dto.request.LearningMaterialRequestDTO;
import com.example.course_service.dto.response.LearningMaterialResponseDTO;
import com.example.course_service.service.LearningMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class LearningMaterialController {

    private final LearningMaterialService materialService;

    @PostMapping
    public ResponseEntity<LearningMaterialResponseDTO> createMaterial( // Kiểu trả về là ResponseDTO
                                                                       @Valid @RequestBody LearningMaterialRequestDTO materialRequestDTO) { // Đầu vào là RequestDTO

        LearningMaterialResponseDTO created = materialService.createMaterial(materialRequestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningMaterialResponseDTO> updateMaterial( // Kiểu trả về là ResponseDTO
                                                                       @PathVariable Long id,
                                                                       @Valid @RequestBody LearningMaterialRequestDTO materialRequestDTO) { // Đầu vào là RequestDTO

        LearningMaterialResponseDTO updated = materialService.updateMaterial(id, materialRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningMaterialResponseDTO> getMaterialById(@PathVariable Long id) { // Kiểu trả về là ResponseDTO

        LearningMaterialResponseDTO material = materialService.getMaterialById(id);
        return ResponseEntity.ok(material);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<LearningMaterialResponseDTO>> getMaterialsByTopicId(@PathVariable Long topicId) { // List ResponseDTO

        List<LearningMaterialResponseDTO> materials = materialService.getMaterialsByTopicId(topicId);
        return ResponseEntity.ok(materials);
    }

    @GetMapping
    public ResponseEntity<List<LearningMaterialResponseDTO>> getAllMaterials() { // List ResponseDTO

        List<LearningMaterialResponseDTO> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
