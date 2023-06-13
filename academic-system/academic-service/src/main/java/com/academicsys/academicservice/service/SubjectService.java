package com.academicsys.academicservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.academicsys.academicservice.dto.SubjectRequest;
import com.academicsys.academicservice.dto.SubjectResponse;
import com.academicsys.academicservice.model.Subject;
import com.academicsys.academicservice.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public void createSubject(SubjectRequest subjectRequest) {
        Subject subject = Subject.builder()
                .code(subjectRequest.getCode())
                .name(subjectRequest.getName())
                .period(subjectRequest.getPeriod())
                .classroom(subjectRequest.getClassroom())
                .build();

        subjectRepository.save(subject);

        log.info("SubjectService.createSubject: Subject saved successfully : subject id = {}", subject.getId());
    }

    public List<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(this::mapToSubjectResponse)
                .collect(Collectors.toList());
    }

    private SubjectResponse mapToSubjectResponse(Subject subject) {
        return SubjectResponse.builder()
                .id(subject.getId())
                .code(subject.getCode())
                .name(subject.getName())
                .period(subject.getPeriod())
                .classroom(subject.getClassroom())
                .build();
    }
}
