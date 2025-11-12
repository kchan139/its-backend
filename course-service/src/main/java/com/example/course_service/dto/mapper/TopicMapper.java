package com.example.course_service.dto.mapper;

import com.example.course_service.dto.TopicDTO;
import com.example.course_service.entity.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public TopicDTO toDTO(Topic topic) {
        return TopicDTO.builder()
                .topicId(topic.getTopicId())
                .name(topic.getName())
                .description(topic.getDescription())
                .difficultyLevel(topic.getDifficultyLevel())
                .subjectId(topic.getSubject().getSubjectId())
                .build();
    }

    public Topic toEntity(TopicDTO dto) {
        return Topic.builder()
                .topicId(dto.getTopicId())
                .name(dto.getName())
                .description(dto.getDescription())
                .difficultyLevel(dto.getDifficultyLevel())
                .build();
    }
}