package com.example.course_service.service.serviceimpl;


import com.example.course_service.dto.SubjectDTO;
import com.example.course_service.dto.mapper.SubjectMapper;
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
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        Subject subject = subjectMapper.toEntity(subjectDTO);
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDTO(savedSubject);
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        subject.setName(subjectDTO.getName());
        subject.setDescription(subjectDTO.getDescription());

        Subject updatedSubject = subjectRepository.save(subject);
        return subjectMapper.toDTO(updatedSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        return subjectMapper.toDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDTO)
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