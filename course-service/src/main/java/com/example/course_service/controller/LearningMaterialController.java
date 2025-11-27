package com.example.course_service.controller;

import com.example.course_service.dto.request.LearningMaterialRequestDTO;
import com.example.course_service.dto.response.LearningMaterialResponseDTO;
import com.example.course_service.service.LearningMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;



@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@Tag(name = "Learning Material", description = "Learning material management with file upload support")
public class LearningMaterialController {

    private final LearningMaterialService materialService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create learning material with file", description = "Teacher only - Upload video/PDF/doc file with metadata")
    public ResponseEntity<LearningMaterialResponseDTO> createMaterial(
            @RequestPart("material") String materialJson,
            @RequestPart(value = "file", required = false) @Parameter(description = "Video, PDF, or document file") MultipartFile file) {

        try {
            LearningMaterialRequestDTO requestDTO = objectMapper.readValue(materialJson, LearningMaterialRequestDTO.class);
            LearningMaterialResponseDTO created = materialService.createMaterial(requestDTO, file);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse material data", e);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update learning material", description = "Teacher only - Update material and optionally replace file")
    public ResponseEntity<LearningMaterialResponseDTO> updateMaterial(
            @PathVariable Long id,
            @RequestPart("material") String materialJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            LearningMaterialRequestDTO requestDTO = objectMapper.readValue(materialJson, LearningMaterialRequestDTO.class);
            LearningMaterialResponseDTO updated = materialService.updateMaterial(id, requestDTO, file);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse material data", e);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get material by ID", description = "Students and Teachers can view")
    public ResponseEntity<LearningMaterialResponseDTO> getMaterialById(@PathVariable Long id) {
        LearningMaterialResponseDTO material = materialService.getMaterialById(id);
        return ResponseEntity.ok(material);
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Get file download URL", description = "Get presigned URL to download the file")
    public ResponseEntity<Map<String, String>> getFileDownloadUrl(@PathVariable Long id) {
        String downloadUrl = materialService.getFileDownloadUrl(id);
        return ResponseEntity.ok(Map.of("downloadUrl", downloadUrl, "expiresIn", "60 minutes"));
    }

    @GetMapping("/topic/{topicId}")
    @Operation(summary = "Get materials by topic", description = "Get all materials for a specific topic")
    public ResponseEntity<List<LearningMaterialResponseDTO>> getMaterialsByTopicId(@PathVariable Long topicId) {
        List<LearningMaterialResponseDTO> materials = materialService.getMaterialsByTopicId(topicId);
        return ResponseEntity.ok(materials);
    }

    @GetMapping
    @Operation(summary = "Get all materials", description = "Get all learning materials")
    public ResponseEntity<List<LearningMaterialResponseDTO>> getAllMaterials() {
        List<LearningMaterialResponseDTO> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete material", description = "Teacher only - Delete material and associated file")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}