package com.example.course_service.service;

import com.example.course_service.dto.TopicDTO;

import java.util.List;

public interface TopicService {
    TopicDTO createTopic(TopicDTO topicDTO);
    TopicDTO updateTopic(Long id, TopicDTO topicDTO);
    TopicDTO getTopicById(Long id);
    List<TopicDTO> getTopicsBySubjectId(Long subjectId);
    List<TopicDTO> getAllTopics();
    void deleteTopic(Long id);
}
