package com.academicsys.academicservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.academicsys.academicservice.dto.SubjectRequest;
import com.academicsys.academicservice.dto.SubjectResponse;
import com.academicsys.academicservice.service.SubjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponse> createSubject(@RequestBody SubjectRequest subjectRequest) {
        SubjectResponse subjectResponse = subjectService.createSubject(subjectRequest);

        if (subjectResponse != null)
            return new ResponseEntity<>(subjectResponse, HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
}
