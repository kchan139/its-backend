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
import com.example.course_service.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LearningMaterialServiceImpl implements LearningMaterialService {

    private final LearningMaterialRepository materialRepository;
    private final TopicRepository topicRepository;
    private final LearningMaterialMapper materialMapper;
    private final MinioService minioService;

    @Override
    public LearningMaterialResponseDTO createMaterial(LearningMaterialRequestDTO requestDTO, MultipartFile file) {
        Topic topic = topicRepository.findById(requestDTO.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        LearningMaterial material = materialMapper.toEntity(requestDTO);
        material.setTopic(topic);

        // Handle file upload
        if (file != null && !file.isEmpty()) {
            String fileName = minioService.uploadFile(file, "materials");
            material.setFileName(fileName);
            material.setFileSize(file.getSize());
            material.setContentType(file.getContentType());
            material.setFileUrl("/api/materials/" + material.getMaterialId() + "/download");
        }


        LearningMaterial savedMaterial = materialRepository.save(material);
        return materialMapper.toResponseDTO(savedMaterial);
    }

    @Override
    public LearningMaterialResponseDTO updateMaterial(Long id, LearningMaterialRequestDTO requestDTO, MultipartFile file) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found"));

        material.setTitle(requestDTO.getTitle());
        material.setContent(requestDTO.getContent());
        material.setType(requestDTO.getType());
        material.setDuration(requestDTO.getDuration());
        // Update topic if changed
        if (requestDTO.getTopicId() != null &&
                (material.getTopic() == null || !material.getTopic().getTopicId().equals(requestDTO.getTopicId()))) {
            Topic newTopic = topicRepository.findById(requestDTO.getTopicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
            material.setTopic(newTopic);
        }

        // Handle file upload/replacement
        if (file != null && !file.isEmpty()) {
            // Upload new file first
            String fileName = minioService.uploadFile(file, "materials");
            // Delete old file if exists, after successful upload
            if (material.getFileName() != null) {
                minioService.deleteFile(material.getFileName());
            }
            material.setFileName(fileName);
            material.setFileSize(file.getSize());
            material.setContentType(file.getContentType());
        }


        LearningMaterial updatedMaterial = materialRepository.save(material);
        return materialMapper.toResponseDTO(updatedMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public LearningMaterialResponseDTO getMaterialById(Long id) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found"));
        return materialMapper.toResponseDTO(material);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningMaterialResponseDTO> getMaterialsByTopicId(Long topicId) {
        return materialRepository.findByTopic_TopicId(topicId).stream()
                .map(materialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningMaterialResponseDTO> getAllMaterials() {
        return materialRepository.findAll().stream()
                .map(materialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMaterial(Long id) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found"));

        try {
            // Delete file from MinIO if exists
            if (material.getFileName() != null) {
                minioService.deleteFile(material.getFileName());
            }
            materialRepository.deleteById(id);
        } catch (Exception e) {
            // Log the error and throw a runtime exception to indicate partial failure
            // You may want to use a logger here instead of System.err
            System.err.println("Failed to delete material or associated file: " + e.getMessage());
            throw new RuntimeException("Failed to delete material or associated file", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getFileDownloadUrl(Long id) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found"));

        if (material.getFileName() == null) {
            throw new RuntimeException("No file attached to this material");
        }

        String internalUrl = minioService.getPresignedUrl(material.getFileName(), 60);

        return internalUrl;
        // return internalUrl.replace("http://its-minio:9000", "http://localhost:9000");
    }
}
