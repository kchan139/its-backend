package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.mapper.LearningMaterialMapper;
import com.example.course_service.dto.request.LearningMaterialRequestDTO;
import com.example.course_service.dto.response.LearningMaterialResponseDTO;
import com.example.course_service.entity.LearningMaterial;
import com.example.course_service.entity.Topic;
import com.example.course_service.exception.ResourceNotFoundException;
import com.example.course_service.repository.LearningMaterialRepository;
import com.example.course_service.repository.TopicRepository;
import com.example.course_service.service.LearningMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LearningMaterialServiceImpl implements LearningMaterialService {

    private final LearningMaterialRepository materialRepository;
    private final TopicRepository topicRepository;
    private final LearningMaterialMapper materialMapper;

    @Override
    public LearningMaterialResponseDTO createMaterial(LearningMaterialRequestDTO materialRequestDTO) {
        // 1. Tìm kiếm và kiểm tra tồn tại của Topic (Khóa ngoại)
        Topic topic = topicRepository.findById(materialRequestDTO.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: "+ materialRequestDTO.getTopicId()));
        LearningMaterial material = materialMapper.toEntity(materialRequestDTO);
        material.setTopic(topic);

        LearningMaterial savedMaterial = materialRepository.save(material);
        return materialMapper.toResponseDTO(savedMaterial);
    }

    @Override
    public LearningMaterialResponseDTO updateMaterial(Long id, LearningMaterialRequestDTO materialRequestDTO) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: "+ id));

        material.setTitle(materialRequestDTO.getTitle());
        material.setContent(materialRequestDTO.getContent());
        material.setType(materialRequestDTO.getType());
        material.setDuration(materialRequestDTO.getDuration());
        Long newTopicId = materialRequestDTO.getTopicId();
        if (newTopicId != null && !newTopicId.equals(material.getTopic().getTopicId())) {
            Topic newTopic = topicRepository.findById(newTopicId)
                    .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: "+ newTopicId));
            material.setTopic(newTopic);
        }

        LearningMaterial updatedMaterial = materialRepository.save(material);
        // 3. Ánh xạ Entity sang Response DTO để trả về
        return materialMapper.toResponseDTO(updatedMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public LearningMaterialResponseDTO getMaterialById(Long id) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: "+ id));
        // Trả về Response DTO
        return materialMapper.toResponseDTO(material);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningMaterialResponseDTO> getMaterialsByTopicId(Long topicId) {
        return materialRepository.findByTopic_TopicId(topicId).stream()
                // Sử dụng phương thức ánh xạ Response mới
                .map(materialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningMaterialResponseDTO> getAllMaterials() {
        return materialRepository.findAll().stream()
                // Sử dụng phương thức ánh xạ Response mới
                .map(materialMapper::toResponseDTO)
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
