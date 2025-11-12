package com.example.course_service.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Subject subject;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<LearningMaterial> learningMaterials = new HashSet<>();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Question> questions = new HashSet<>();
}