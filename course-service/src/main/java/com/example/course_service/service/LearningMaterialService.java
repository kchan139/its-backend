package com.example.course_service.service;

import com.example.course_service.dto.request.LearningMaterialRequestDTO;
import com.example.course_service.dto.response.LearningMaterialResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LearningMaterialService {
    LearningMaterialResponseDTO createMaterial(LearningMaterialRequestDTO requestDTO, MultipartFile file);
    LearningMaterialResponseDTO updateMaterial(Long id, LearningMaterialRequestDTO requestDTO, MultipartFile file);
    LearningMaterialResponseDTO getMaterialById(Long id);
    List<LearningMaterialResponseDTO> getMaterialsByTopicId(Long topicId);
    List<LearningMaterialResponseDTO> getAllMaterials();
    void deleteMaterial(Long id);
    String getFileDownloadUrl(Long id);
}
