package com.example.course_service.service;

import com.example.course_service.dto.request.QuestionRequestDTO;
import com.example.course_service.dto.response.QuestionResponseDTO;

import java.util.List;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO);

    QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO questionRequestDTO);

    QuestionResponseDTO getQuestionById(Long id);

    List<QuestionResponseDTO> getQuestionsByTopicId(Long topicId);

    List<QuestionResponseDTO> getAllQuestions();

    void deleteQuestion(Long id);
}