package com.example.course_service.dto.mapper;

import com.example.course_service.dto.request.QuestionRequestDTO;
import com.example.course_service.dto.response.QuestionResponseDTO;
import com.example.course_service.entity.Question;
import com.example.course_service.entity.Topic;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionResponseDTO toResponseDTO(Question question) {
        return QuestionResponseDTO.builder()
                .questionId(question.getQuestionId())
                .text(question.getText())
                .type(question.getType())
                .correctAnswer(question.getCorrectAnswer())
                .hintContent(question.getHintContent())
                // Lấy topicId từ Entity
                .topicId(question.getTopic() != null ? question.getTopic().getTopicId() : null)
                .build();
    }

    // Ánh xạ Request DTO -> Entity (Nhận từ Client)
    public Question toEntity(QuestionRequestDTO dto) {

        // Khởi tạo một đối tượng Topic với ID để thiết lập mối quan hệ
        // Service Layer sẽ chịu trách nhiệm kiểm tra xem ID này có tồn tại không
        Topic topic = Topic.builder().topicId(dto.getTopicId()).build();

        return Question.builder()
                // Bỏ questionId khi tạo mới
                .text(dto.getText())
                .type(dto.getType())
                .correctAnswer(dto.getCorrectAnswer())
                .hintContent(dto.getHintContent())
                .topic(topic) // Gán đối tượng Topic với ID
                .build();
    }
}
