package com.example.course_service.dto.mapper;

import com.example.course_service.dto.LearningMaterialDTO;
import com.example.course_service.entity.LearningMaterial;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class LearningMaterialMapper {

    public LearningMaterialDTO toDTO(LearningMaterial material) {
        return LearningMaterialDTO.builder()
                .materialId(material.getMaterialId())
                .title(material.getTitle())
                .content(material.getContent())
                .type(material.getType())
                .duration(material.getDuration())
                .topicId(material.getTopic().getTopicId())
                .tagIds(material.getTags().stream()
                        .map(tag -> tag.getTagId())
                        .collect(Collectors.toSet()))
                .build();
    }

    public LearningMaterial toEntity(LearningMaterialDTO dto) {
        return LearningMaterial.builder()
                .materialId(dto.getMaterialId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .type(dto.getType())
                .duration(dto.getDuration())
                .build();
    }
}
