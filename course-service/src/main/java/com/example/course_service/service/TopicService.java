package com.example.course_service.service;

import com.example.course_service.dto.request.TopicRequestDTO;
import com.example.course_service.dto.response.TopicResponseDTO;

import java.util.List;

public interface TopicService {
    TopicResponseDTO createTopic(TopicRequestDTO topicRequestDTO);
    TopicResponseDTO updateTopic(Long id, TopicRequestDTO topicRequestDTO);

    TopicResponseDTO getTopicById(Long id);

    List<TopicResponseDTO> getTopicsBySubjectId(Long subjectId);

    List<TopicResponseDTO> getAllTopics();

    void deleteTopic(Long id);
}
