package com.example.course_service.service;

import com.example.course_service.dto.LearningMaterialDTO;

import java.util.List;

public interface LearningMaterialService {
    LearningMaterialDTO createMaterial(LearningMaterialDTO materialDTO);
    LearningMaterialDTO updateMaterial(Long id, LearningMaterialDTO materialDTO);
    LearningMaterialDTO getMaterialById(Long id);
    List<LearningMaterialDTO> getMaterialsByTopicId(Long topicId);
    List<LearningMaterialDTO> getAllMaterials();
    void deleteMaterial(Long id);
}
