package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.mapper.FeedbackMapper;
import com.example.course_service.dto.request.FeedbackRequestDTO;
import com.example.course_service.dto.response.FeedbackResponseDTO;
import com.example.course_service.entity.Feedback;
import com.example.course_service.entity.Subject;
import com.example.course_service.exception.ResourceNotFoundException;
import com.example.course_service.repository.FeedbackRepository;
import com.example.course_service.repository.SubjectRepository;
import com.example.course_service.service.FeedbackService;
import org.springframework.security.core.Authentication;
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
    // Chữ ký mới: nhận RequestDTO và Authentication
    public FeedbackResponseDTO createFeedback(FeedbackRequestDTO feedbackRequestDTO, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new org.springframework.security.access.AccessDeniedException("User is not authenticated.");
        }
        String userIdString = authentication.getName();
        Long studentId = Long.parseLong(userIdString);

        // 2. KIỂM TRA TỒN TẠI CỦA KHÓA NGOẠI
        Subject subject = subjectRepository.findById(feedbackRequestDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        Feedback feedback = feedbackMapper.toEntity(feedbackRequestDTO, studentId);

        // Gán các đối tượng Entity đã được quản lý (managed entities)
        feedback.setSubject(subject);
        feedback.setStudentId(studentId);

        Feedback savedFeedback = feedbackRepository.save(feedback);

        // 4. Trả về Response DTO
        return feedbackMapper.toResponseDTO(savedFeedback);
    }

    @Override
    @Transactional(readOnly = true)
    // Chữ ký mới: trả về ResponseDTO
    public FeedbackResponseDTO getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
        return feedbackMapper.toResponseDTO(feedback);
    }

    @Override
    @Transactional(readOnly = true)
    // Chữ ký mới: List ResponseDTO
    public List<FeedbackResponseDTO> getFeedbacksBySubjectId(Long subjectId) {
        return feedbackRepository.findBySubject_SubjectId(subjectId).stream()
                .map(feedbackMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    // Chữ ký mới: List ResponseDTO
    public List<FeedbackResponseDTO> getAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(feedbackMapper::toResponseDTO)
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
