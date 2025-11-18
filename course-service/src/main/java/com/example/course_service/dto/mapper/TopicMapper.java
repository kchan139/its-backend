package com.example.course_service.dto.mapper;

import com.example.course_service.dto.request.TopicRequestDTO;
import com.example.course_service.dto.response.TopicResponseDTO;
import com.example.course_service.entity.Subject;
import com.example.course_service.entity.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public TopicResponseDTO toResponseDTO(Topic topic) {
        return TopicResponseDTO.builder()
                .topicId(topic.getTopicId())
                .name(topic.getName())
                .description(topic.getDescription())
                .difficultyLevel(topic.getDifficultyLevel())
                .subjectId(topic.getSubject() != null ? topic.getSubject().getSubjectId() : null)
                .build();
    }

    // Ánh xạ Request DTO -> Entity (Nhận từ Client)
    public Topic toEntity(TopicRequestDTO dto) {
        // Khởi tạo một đối tượng Subject với ID để thiết lập mối quan hệ
        // Service Layer sẽ chịu trách nhiệm kiểm tra xem ID này có tồn tại không
        Subject subject = Subject.builder().subjectId(dto.getSubjectId()).build();

        return Topic.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .difficultyLevel(dto.getDifficultyLevel())
                .subject(subject) // Gán đối tượng Subject với ID
                .build();
    }
}