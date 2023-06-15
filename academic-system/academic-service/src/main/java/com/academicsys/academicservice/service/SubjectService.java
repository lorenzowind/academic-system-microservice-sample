package com.academicsys.academicservice.service;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.academicsys.academicservice.dto.*;
import com.academicsys.academicservice.model.Subject;
import com.academicsys.academicservice.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectResponse createSubject(SubjectRequest subjectRequest) {
        Predicate<Subject> isDuplicated = subject ->
                Objects.equals(subject.getCode(), subjectRequest.getCode())
                        && Objects.equals(subject.getClassroom(), subjectRequest.getClassroom());

        if (subjectRepository.findAll().stream().anyMatch(isDuplicated)) {
            log.error("SubjectService.createSubject: Subject can't be added : subject code = {} : subject classroom = {}", subjectRequest.getCode(), subjectRequest.getClassroom());
            return null;
        }

        Subject subject = Subject.builder()
                .code(subjectRequest.getCode())
                .name(subjectRequest.getName())
                .period(subjectRequest.getPeriod())
                .classroom(subjectRequest.getClassroom())
                .build();

        subjectRepository.save(subject);

        log.info("SubjectService.createSubject: Subject saved successfully : subject id = {}", subject.getId());

        return mapToSubjectResponse(subject);
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
