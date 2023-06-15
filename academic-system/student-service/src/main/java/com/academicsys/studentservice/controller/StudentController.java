package com.academicsys.studentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.academicsys.studentservice.dtos.StudentRequest;
import com.academicsys.studentservice.dtos.StudentResponse;
import com.academicsys.studentservice.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        StudentResponse studentResponse = studentService.createStudent(studentRequest);

        if (studentResponse != null)
            return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/name={name}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getStudentsByName(@PathVariable("name") String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/registration={registration}")
    public ResponseEntity<StudentResponse> getStudentByRegistration(@PathVariable("registration") String name) {
        StudentResponse studentResponse = studentService.getStudentByRegistration(name);

        if (studentResponse != null)
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
