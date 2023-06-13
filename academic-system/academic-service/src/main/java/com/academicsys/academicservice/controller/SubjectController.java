package com.academicsys.academicservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubject(@RequestBody SubjectRequest subjectRequest) {
        subjectService.createSubject(subjectRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
}
