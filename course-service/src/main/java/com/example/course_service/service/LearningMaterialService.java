package com.example.course_service.service;

import com.example.course_service.dto.request.LearningMaterialRequestDTO;
import com.example.course_service.dto.response.LearningMaterialResponseDTO;

import java.util.List;

public interface LearningMaterialService {
    LearningMaterialResponseDTO createMaterial(LearningMaterialRequestDTO materialRequestDTO);

    // Thay đổi đầu vào và đầu ra
    LearningMaterialResponseDTO updateMaterial(Long id, LearningMaterialRequestDTO materialRequestDTO);

    // Thay đổi đầu ra
    LearningMaterialResponseDTO getMaterialById(Long id);

    // Thay đổi đầu ra
    List<LearningMaterialResponseDTO> getMaterialsByTopicId(Long topicId);

    // Thay đổi đầu ra
    List<LearningMaterialResponseDTO> getAllMaterials();

    void deleteMaterial(Long id);
}
