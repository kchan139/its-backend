package com.example.course_service.dto.mapper;

import com.example.course_service.dto.request.LearningMaterialRequestDTO;
import com.example.course_service.dto.response.LearningMaterialResponseDTO;
import com.example.course_service.entity.LearningMaterial;
import com.example.course_service.entity.Topic;
import org.springframework.stereotype.Component;


@Component
public class LearningMaterialMapper {

    public LearningMaterialResponseDTO toResponseDTO(LearningMaterial material) {
        return LearningMaterialResponseDTO.builder()
                .materialId(material.getMaterialId())
                .title(material.getTitle())
                .content(material.getContent())
                .type(material.getType())
                .duration(material.getDuration())
                .topicId(material.getTopic() != null ? material.getTopic().getTopicId() : null)
                .fileName(material.getFileName())
                .fileUrl(material.getFileUrl())
                .fileSize(material.getFileSize())
                .contentType(material.getContentType())
                .build();
    }

    public LearningMaterial toEntity(LearningMaterialRequestDTO requestDTO) {
        Topic topic = Topic.builder().topicId(requestDTO.getTopicId()).build();
        return LearningMaterial.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .type(requestDTO.getType())
                .duration(requestDTO.getDuration())
                .topic(topic)
                .build();
    }
}
