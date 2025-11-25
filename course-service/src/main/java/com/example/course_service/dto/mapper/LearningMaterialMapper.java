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
                .build();
    }

    // Ánh xạ Request DTO -> Entity (Nhận từ Client)
    public LearningMaterial toEntity(LearningMaterialRequestDTO dto) {

        Topic topic = Topic.builder().topicId(dto.getTopicId()).build();
        return LearningMaterial.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .type(dto.getType())
                .duration(dto.getDuration())
                .topic(topic) // Gán đối tượng Topic với ID
                .build();
    }
}
