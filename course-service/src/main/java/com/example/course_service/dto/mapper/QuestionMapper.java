package com.example.course_service.dto.mapper;

import com.example.course_service.dto.QuestionDTO;
import com.example.course_service.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionDTO toDTO(Question question) {
        return QuestionDTO.builder()
                .questionId(question.getQuestionId())
                .text(question.getText())
                .type(question.getType())
                .correctAnswer(question.getCorrectAnswer())
                .hintContent(question.getHintContent())
                .topicId(question.getTopic() != null ? question.getTopic().getTopicId() : null)
                .build();
    }

    public Question toEntity(QuestionDTO dto) {
        return Question.builder()
                .questionId(dto.getQuestionId())
                .text(dto.getText())
                .type(dto.getType())
                .correctAnswer(dto.getCorrectAnswer())
                .hintContent(dto.getHintContent())
                .build();
    }
}
