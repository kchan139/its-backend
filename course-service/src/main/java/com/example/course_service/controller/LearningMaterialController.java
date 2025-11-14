package com.example.course_service.controller;

import com.example.course_service.dto.LearningMaterialDTO;
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
    public ResponseEntity<LearningMaterialDTO> createMaterial(@Valid @RequestBody LearningMaterialDTO materialDTO) {
        LearningMaterialDTO created = materialService.createMaterial(materialDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningMaterialDTO> updateMaterial(@PathVariable Long id,
                                                              @Valid @RequestBody LearningMaterialDTO materialDTO) {
        LearningMaterialDTO updated = materialService.updateMaterial(id, materialDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningMaterialDTO> getMaterialById(@PathVariable Long id) {
        LearningMaterialDTO material = materialService.getMaterialById(id);
        return ResponseEntity.ok(material);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<LearningMaterialDTO>> getMaterialsByTopicId(@PathVariable Long topicId) {
        List<LearningMaterialDTO> materials = materialService.getMaterialsByTopicId(topicId);
        return ResponseEntity.ok(materials);
    }

    @GetMapping
    public ResponseEntity<List<LearningMaterialDTO>> getAllMaterials() {
        List<LearningMaterialDTO> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
