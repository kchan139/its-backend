package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.FeedbackDTO;
import com.example.course_service.dto.mapper.FeedbackMapper;
import com.example.course_service.entity.Feedback;
import com.example.course_service.entity.Subject;
import com.example.course_service.exception.ResourceNotFoundException;
import com.example.course_service.repository.FeedbackRepository;
import com.example.course_service.repository.SubjectRepository;
import com.example.course_service.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final SubjectRepository subjectRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO feedbackDTO) {
        Subject subject = subjectRepository.findById(feedbackDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback.setSubject(subject);

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDTO(savedFeedback);
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackDTO getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
        return feedbackMapper.toDTO(feedback);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> getFeedbacksBySubjectId(Long subjectId) {
        return feedbackRepository.findBySubject_SubjectId(subjectId).stream()
                .map(feedbackMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> getAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(feedbackMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Feedback not found");
        }
        feedbackRepository.deleteById(id);
    }
}
