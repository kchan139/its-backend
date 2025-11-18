package com.example.course_service.controller;

import com.example.course_service.dto.request.TopicRequestDTO;
import com.example.course_service.dto.response.TopicResponseDTO;
import com.example.course_service.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicResponseDTO> createTopic( // Kiểu trả về là ResponseDTO
                                                         @Valid @RequestBody TopicRequestDTO topicRequestDTO) { // Đầu vào là RequestDTO

        TopicResponseDTO created = topicService.createTopic(topicRequestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic( // Kiểu trả về là ResponseDTO
                                                         @PathVariable Long id,
                                                         @Valid @RequestBody TopicRequestDTO topicRequestDTO) { // Đầu vào là RequestDTO

        TopicResponseDTO updated = topicService.updateTopic(id, topicRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getTopicById(@PathVariable Long id) { // Kiểu trả về là ResponseDTO

        TopicResponseDTO topic = topicService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<TopicResponseDTO>> getTopicsBySubjectId(@PathVariable Long subjectId) { // List ResponseDTO

        List<TopicResponseDTO> topics = topicService.getTopicsBySubjectId(subjectId);
        return ResponseEntity.ok(topics);
    }

    @GetMapping
    public ResponseEntity<List<TopicResponseDTO>> getAllTopics() { // List ResponseDTO

        List<TopicResponseDTO> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
