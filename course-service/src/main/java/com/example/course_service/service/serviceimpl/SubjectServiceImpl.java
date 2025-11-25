package com.example.course_service.service.serviceimpl;

import com.example.course_service.dto.mapper.SubjectMapper;
import com.example.course_service.dto.request.SubjectRequestDTO;
import com.example.course_service.dto.response.SubjectResponseDTO;
import com.example.course_service.entity.Subject;
import com.example.course_service.exception.ResourceNotFoundException;
import com.example.course_service.repository.SubjectRepository;
import com.example.course_service.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectResponseDTO createSubject(SubjectRequestDTO subjectRequestDTO) {
        // Ánh xạ từ Request DTO sang Entity
        Subject subject = subjectMapper.toEntity(subjectRequestDTO);
        Subject savedSubject = subjectRepository.save(subject);
        // Ánh xạ từ Entity sang Response DTO
        return subjectMapper.toResponseDTO(savedSubject);
    }

    @Override
    public SubjectResponseDTO updateSubject(Long id, SubjectRequestDTO subjectRequestDTO) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        // Cập nhật Entity từ Request DTO
        subject.setName(subjectRequestDTO.getName());
        subject.setDescription(subjectRequestDTO.getDescription());

        Subject updatedSubject = subjectRepository.save(subject);
        // Trả về Response DTO
        return subjectMapper.toResponseDTO(updatedSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        return subjectMapper.toResponseDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }
}