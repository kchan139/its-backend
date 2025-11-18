package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.mapper.TopicMapper;
import com.example.course_service.dto.request.TopicRequestDTO;
import com.example.course_service.dto.response.TopicResponseDTO;
import com.example.course_service.entity.Subject;
import com.example.course_service.entity.Topic;
import com.example.course_service.exception.ResourceNotFoundException;
import com.example.course_service.repository.SubjectRepository;
import com.example.course_service.repository.TopicRepository;
import com.example.course_service.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final SubjectRepository subjectRepository;
    private final TopicMapper topicMapper;

    @Override
    public TopicResponseDTO createTopic(TopicRequestDTO topicRequestDTO) {
        // 1. Tìm kiếm và kiểm tra tồn tại của Subject (Khóa ngoại)
        Subject subject = subjectRepository.findById(topicRequestDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: "+ topicRequestDTO.getSubjectId()));

        Topic topic = topicMapper.toEntity(topicRequestDTO);

        topic.setSubject(subject);

        Topic savedTopic = topicRepository.save(topic);

        return topicMapper.toResponseDTO(savedTopic);
    }

    @Override
    public TopicResponseDTO updateTopic(Long id, TopicRequestDTO topicRequestDTO) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: "+ id));

        // 1. Cập nhật các trường thuộc tính cơ bản
        topic.setName(topicRequestDTO.getName());
        topic.setDescription(topicRequestDTO.getDescription());
        topic.setDifficultyLevel(topicRequestDTO.getDifficultyLevel());

        // 2. Kiểm tra và cập nhật Khóa ngoại (Subject) nếu có thay đổi
        Long newSubjectId = topicRequestDTO.getSubjectId();
        if (newSubjectId != null && !newSubjectId.equals(topic.getSubject().getSubjectId())) {
            Subject newSubject = subjectRepository.findById(newSubjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: "+ newSubjectId));
            topic.setSubject(newSubject);
        }

        Topic updatedTopic = topicRepository.save(topic);
        // 3. Ánh xạ Entity sang Response DTO để trả về
        return topicMapper.toResponseDTO(updatedTopic);
    }

    @Override
    @Transactional(readOnly = true)
    public TopicResponseDTO getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: "+ id));
        // Trả về Response DTO
        return topicMapper.toResponseDTO(topic);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopicResponseDTO> getTopicsBySubjectId(Long subjectId) {
        return topicRepository.findBySubject_SubjectId(subjectId).stream()
                // Sử dụng phương thức ánh xạ Response mới
                .map(topicMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopicResponseDTO> getAllTopics() {
        return topicRepository.findAll().stream()
                // Sử dụng phương thức ánh xạ Response mới
                .map(topicMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTopic(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic not found with id: "+ id);
        }
        topicRepository.deleteById(id);
    }
}

