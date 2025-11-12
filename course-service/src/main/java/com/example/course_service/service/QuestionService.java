package com.example.course_service.service;

import com.example.course_service.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {
    QuestionDTO createQuestion(QuestionDTO questionDTO);
    QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO);
    QuestionDTO getQuestionById(Long id);
    List<QuestionDTO> getQuestionsByTopicId(Long topicId);
    List<QuestionDTO> getAllQuestions();
    void deleteQuestion(Long id);
}