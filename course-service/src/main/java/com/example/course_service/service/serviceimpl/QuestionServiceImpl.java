package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.mapper.QuestionMapper;
import com.example.course_service.dto.request.QuestionRequestDTO;
import com.example.course_service.dto.response.QuestionResponseDTO;
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
        public QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO) {
            // 1. Tìm kiếm và kiểm tra tồn tại của Topic (Khóa ngoại)
            Topic topic = topicRepository.findById(questionRequestDTO.getTopicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

            // 2. Ánh xạ từ Request DTO sang Entity (chỉ các trường thuộc tính)
            Question question = questionMapper.toEntity(questionRequestDTO);

            // 3. Gán đối tượng Topic đã được quản lý vào Entity
            question.setTopic(topic);

            Question savedQuestion = questionRepository.save(question);

            // 4. Ánh xạ Entity sang Response DTO để trả về
            return questionMapper.toResponseDTO(savedQuestion);
        }

        @Override
        public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO questionRequestDTO) {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

            // 1. Cập nhật các trường thuộc tính cơ bản
            question.setText(questionRequestDTO.getText());
            question.setType(questionRequestDTO.getType());
            question.setCorrectAnswer(questionRequestDTO.getCorrectAnswer());
            question.setHintContent(questionRequestDTO.getHintContent());

            // 2. Kiểm tra và cập nhật Khóa ngoại (Topic) nếu có thay đổi
            Long newTopicId = questionRequestDTO.getTopicId();
            if (newTopicId != null && (question.getTopic() == null || !newTopicId.equals(question.getTopic().getTopicId()))) {
                Topic newTopic = topicRepository.findById(newTopicId)
                        .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
                question.setTopic(newTopic);
            }

            Question updatedQuestion = questionRepository.save(question);
            // 3. Ánh xạ Entity sang Response DTO để trả về
            return questionMapper.toResponseDTO(updatedQuestion);
        }

        @Override
        @Transactional(readOnly = true)
        public QuestionResponseDTO getQuestionById(Long id) {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
            // Trả về Response DTO
            return questionMapper.toResponseDTO(question);
        }

        @Override
        @Transactional(readOnly = true)
        public List<QuestionResponseDTO> getQuestionsByTopicId(Long topicId) {
            return questionRepository.findByTopic_TopicId(topicId).stream()
                    // Sử dụng phương thức ánh xạ Response mới
                    .map(questionMapper::toResponseDTO)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<QuestionResponseDTO> getAllQuestions() {
            return questionRepository.findAll().stream()
                    // Sử dụng phương thức ánh xạ Response mới
                    .map(questionMapper::toResponseDTO)
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
