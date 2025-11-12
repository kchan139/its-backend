package com.example.course_service.service.serviceimpl;


import com.example.course_service.dto.TopicDTO;
import com.example.course_service.dto.mapper.TopicMapper;
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
    public TopicDTO createTopic(TopicDTO topicDTO) {
        Subject subject = subjectRepository.findById(topicDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: "+ topicDTO.getSubjectId()));

        Topic topic = topicMapper.toEntity(topicDTO);
        topic.setSubject(subject);

        Topic savedTopic = topicRepository.save(topic);
        return topicMapper.toDTO(savedTopic);
    }

    @Override
    public TopicDTO updateTopic(Long id, TopicDTO topicDTO) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with  id: "+ id));

        topic.setName(topicDTO.getName());
        topic.setDescription(topicDTO.getDescription());
        topic.setDifficultyLevel(topicDTO.getDifficultyLevel());

        Topic updatedTopic = topicRepository.save(topic);
        return topicMapper.toDTO(updatedTopic);
    }

    @Override
    @Transactional(readOnly = true)
    public TopicDTO getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: "+ id));
        return topicMapper.toDTO(topic);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopicDTO> getTopicsBySubjectId(Long subjectId) {
        return topicRepository.findBySubject_SubjectId(subjectId).stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topicMapper::toDTO)
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

