package com.example.course_service.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(columnDefinition = "TEXT")
    private String hintContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Topic topic;
}
