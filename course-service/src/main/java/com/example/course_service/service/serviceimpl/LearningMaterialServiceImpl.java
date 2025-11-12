package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.LearningMaterialDTO;
import com.example.course_service.dto.mapper.LearningMaterialMapper;
import com.example.course_service.entity.LearningMaterial;
import com.example.course_service.entity.Tag;
import com.example.course_service.entity.Topic;
import com.example.course_service.exception.ResourceNotFoundException;
import com.example.course_service.repository.LearningMaterialRepository;
import com.example.course_service.repository.TagRepository;
import com.example.course_service.repository.TopicRepository;
import com.example.course_service.service.LearningMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LearningMaterialServiceImpl implements LearningMaterialService {

    private final LearningMaterialRepository materialRepository;
    private final TopicRepository topicRepository;
    private final TagRepository tagRepository;
    private final LearningMaterialMapper materialMapper;

    @Override
    public LearningMaterialDTO createMaterial(LearningMaterialDTO materialDTO) {
        Topic topic = topicRepository.findById(materialDTO.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: "+ materialDTO.getTopicId()));

        LearningMaterial material = materialMapper.toEntity(materialDTO);
        material.setTopic(topic);

        if (materialDTO.getTagIds() != null && !materialDTO.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : materialDTO.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagId));
                tags.add(tag);
            }
            material.setTags(tags);
        }

        LearningMaterial savedMaterial = materialRepository.save(material);
        return materialMapper.toDTO(savedMaterial);
    }

    @Override
    public LearningMaterialDTO updateMaterial(Long id, LearningMaterialDTO materialDTO) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: "+ id));

        material.setTitle(materialDTO.getTitle());
        material.setContent(materialDTO.getContent());
        material.setType(materialDTO.getType());
        material.setDuration(materialDTO.getDuration());

        if (materialDTO.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : materialDTO.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagId));
                tags.add(tag);
            }
            material.setTags(tags);
        }

        LearningMaterial updatedMaterial = materialRepository.save(material);
        return materialMapper.toDTO(updatedMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public LearningMaterialDTO getMaterialById(Long id) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: "+ id));
        return materialMapper.toDTO(material);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningMaterialDTO> getMaterialsByTopicId(Long topicId) {
        return materialRepository.findByTopic_TopicId(topicId).stream()
                .map(materialMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningMaterialDTO> getAllMaterials() {
        return materialRepository.findAll().stream()
                .map(materialMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Learning material not found with id: "+ id);
        }
        materialRepository.deleteById(id);
    }
}
