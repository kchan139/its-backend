package com.example.course_service.controller;

import com.example.course_service.dto.TopicDTO;
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
    public ResponseEntity<TopicDTO> createTopic(@Valid @RequestBody TopicDTO topicDTO) {
        TopicDTO created = topicService.createTopic(topicDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> updateTopic(@PathVariable Long id,
                                                @Valid @RequestBody TopicDTO topicDTO) {
        TopicDTO updated = topicService.updateTopic(id, topicDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id) {
        TopicDTO topic = topicService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<TopicDTO>> getTopicsBySubjectId(@PathVariable Long subjectId) {
        List<TopicDTO> topics = topicService.getTopicsBySubjectId(subjectId);
        return ResponseEntity.ok(topics);
    }

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<TopicDTO> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
