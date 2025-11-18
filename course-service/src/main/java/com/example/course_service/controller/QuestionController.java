package com.example.course_service.controller;

import com.example.course_service.dto.request.QuestionRequestDTO;
import com.example.course_service.dto.response.QuestionResponseDTO;
import com.example.course_service.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion( // Kiểu trả về là ResponseDTO
                                                               @Valid @RequestBody QuestionRequestDTO questionRequestDTO) { // Đầu vào là RequestDTO

        QuestionResponseDTO created = questionService.createQuestion(questionRequestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> updateQuestion( // Kiểu trả về là ResponseDTO
                                                               @PathVariable Long id,
                                                               @Valid @RequestBody QuestionRequestDTO questionRequestDTO) { // Đầu vào là RequestDTO

        QuestionResponseDTO updated = questionService.updateQuestion(id, questionRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) { // Kiểu trả về là ResponseDTO

        QuestionResponseDTO question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<QuestionResponseDTO>> getQuestionsByTopicId(@PathVariable Long topicId) { // List ResponseDTO

        List<QuestionResponseDTO> questions = questionService.getQuestionsByTopicId(topicId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestions() { // List ResponseDTO

        List<QuestionResponseDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
