package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.QuestionDTO;
import com.example.course_service.dto.mapper.QuestionMapper;
import com.example.course_service.entity.Question;
import com.example.course_service.entity.Topic;
import com.example.course_service.exception.ResourceNotFoundException;
import com.example.course_service.repository.QuestionRepository;
import com.example.course_service.repository.TopicRepository;
import com.example.course_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Topic topic = topicRepository.findById(questionDTO.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        Question question = questionMapper.toEntity(questionDTO);
        question.setTopic(topic);

        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(savedQuestion);
    }

    @Override
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        question.setText(questionDTO.getText());
        question.setType(questionDTO.getType());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setHintContent(questionDTO.getHintContent());

        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(updatedQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        return questionMapper.toDTO(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> getQuestionsByTopicId(Long topicId) {
        return questionRepository.findByTopic_TopicId(topicId).stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found");
        }
        questionRepository.deleteById(id);
    }
}
