package com.academicsys.academicservice.controller;

import com.academicsys.academicservice.dto.EnrollmentRequest;
import com.academicsys.academicservice.dto.EnrollmentResponse;
import com.academicsys.academicservice.dto.SubjectEnrollmentsRequest;
import com.academicsys.academicservice.service.EnrollmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enrollStudent(@RequestBody EnrollmentRequest enrollmentRequest) {
        EnrollmentResponse enrollmentResponse = enrollmentService.enrollStudent(enrollmentRequest);

        if (enrollmentResponse != null)
            return new ResponseEntity<>(enrollmentResponse, HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/registration={registration}")
    @ResponseStatus(HttpStatus.OK)
    public List<EnrollmentResponse> getEnrolledSubjects(@PathVariable("registration") String registration) {
        return enrollmentService.getEnrolledSubjects(registration);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnrollmentResponse> getSubjectEnrollments(@RequestBody SubjectEnrollmentsRequest subjectEnrollmentsRequest) {
        return enrollmentService.getSubjectEnrollments(subjectEnrollmentsRequest);
    }
}
