package com.academicsys.academicservice.service;

import com.academicsys.academicservice.dto.*;
import com.academicsys.academicservice.model.Enrollment;
import com.academicsys.academicservice.model.Subject;
import com.academicsys.academicservice.repository.EnrollmentRepository;
import com.academicsys.academicservice.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final WebClient webClient;

    public EnrollmentResponse enrollStudent(EnrollmentRequest enrollmentRequest) {
        Predicate<Subject> isValid = subject ->
                Objects.equals(subject.getCode(), enrollmentRequest.getCode())
                        && Objects.equals(subject.getClassroom(), enrollmentRequest.getClassroom());

        if (subjectRepository.findAll().stream().noneMatch(isValid)) {
            log.error("SubjectService.enrollStudent: Subject not valid : subject code = {} : subject classroom = {}", enrollmentRequest.getCode(), enrollmentRequest.getClassroom());
            return null;
        }

        ResponseEntity<StudentResponse> studentResponse = webClient.get()
                .uri("http://localhost:8082/api/student/registration=" + enrollmentRequest.getRegistration())
                .retrieve()
                .toEntity(StudentResponse.class)
                .block();

        if (studentResponse == null || !studentResponse.hasBody()) {
            log.error("SubjectService.enrollStudent: Student not available : student registration = {}", enrollmentRequest.getRegistration());
            return null;
        }

        Predicate<Enrollment> isDuplicated = enrollment ->
                Objects.equals(enrollment.getRegistration(), enrollmentRequest.getRegistration())
                        && Objects.equals(enrollment.getCode(), enrollmentRequest.getCode())
                        && Objects.equals(enrollment.getClassroom(), enrollmentRequest.getClassroom());

        if (enrollmentRepository.findAll().stream().anyMatch(isDuplicated)) {
            log.error("SubjectService.enrollStudent: Student already enrolled : student registration = {} : subject code = {} : subject classroom = {}", enrollmentRequest.getRegistration(), enrollmentRequest.getCode(), enrollmentRequest.getClassroom());
            return null;
        }

        Enrollment enrollment = Enrollment.builder()
                .registration(enrollmentRequest.getRegistration())
                .code(enrollmentRequest.getCode())
                .classroom(enrollmentRequest.getClassroom())
                .build();

        enrollmentRepository.save(enrollment);

        log.info("SubjectService.enrollStudent: Student enrolled successfully : student registration = {} : subject code = {} : subject classroom = {}", enrollmentRequest.getRegistration(), enrollmentRequest.getCode(), enrollmentRequest.getClassroom());

        return mapToEnrollmentResponse(enrollment);
    }

    public List<EnrollmentResponse> getEnrolledSubjects(String registration) {
        return enrollmentRepository
                .findByRegistration(registration)
                .stream()
                .map(this::mapToEnrollmentResponse)
                .collect(Collectors.toList());
    }

    public List<EnrollmentResponse> getSubjectEnrollments(SubjectEnrollmentsRequest subjectEnrollmentsRequest) {
        Predicate<Enrollment> isEnrolled = enrollment ->
                Objects.equals(enrollment.getCode(), subjectEnrollmentsRequest.getCode())
                        && Objects.equals(enrollment.getClassroom(), subjectEnrollmentsRequest.getClassroom());

        return enrollmentRepository
                .findAll()
                .stream()
                .filter(isEnrolled)
                .map(this::mapToEnrollmentResponse)
                .collect(Collectors.toList());
    }

    private EnrollmentResponse mapToEnrollmentResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .registration(enrollment.getRegistration())
                .code(enrollment.getCode())
                .classroom(enrollment.getClassroom())
                .build();
    }
}
