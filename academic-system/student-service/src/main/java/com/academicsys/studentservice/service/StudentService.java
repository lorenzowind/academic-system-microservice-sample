package com.academicsys.studentservice.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.academicsys.studentservice.dtos.StudentRequest;
import com.academicsys.studentservice.dtos.StudentResponse;
import com.academicsys.studentservice.model.Student;
import com.academicsys.studentservice.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Math.pow;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private static final int REGISTRATION_DIGITS = 8;

    private final StudentRepository studentRepository;

    public StudentResponse createStudent(StudentRequest studentRequest) {
        /*if (studentRepository.findByIdentification(studentRequest.getIdentification()).isPresent()) {
            log.error("StudentService.createStudent: Student already added : student identification = {}", studentRequest.getIdentification());
            return null;
        }*/

        String registration = generateRegistration();

        if (registration.equals("")) {
            log.error("StudentService.createStudent: There's no registration available");
            return null;
        }

        Student student = Student.builder()
                .name(studentRequest.getName())
                .identification(studentRequest.getIdentification())
                .address(studentRequest.getAddress())
                .registration(registration)
                .build();

        studentRepository.save(student);

        log.info("StudentService.createStudent: Student saved successfully : student id = {}", student.getId());
        return mapToStudentResponse(student);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::mapToStudentResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentByRegistration(String registration) {
        return studentRepository
                .findByRegistration(registration)
                .map(this::mapToStudentResponse)
                .orElse(null);
    }

    public List<StudentResponse> getStudentsByName(String name) {
        return studentRepository
                .findByNameLike(name)
                .stream()
                .map(this::mapToStudentResponse)
                .collect(Collectors.toList());
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .identification(student.getIdentification())
                .address(student.getAddress())
                .registration(student.getRegistration())
                .build();
    }

    private String generateRegistration() {
        Random random = new Random();
        int minDigitsFactor = (int)pow(10, REGISTRATION_DIGITS);
        int maxDigitsFactor = 9 * minDigitsFactor;

        if (studentRepository.count() >= maxDigitsFactor)
            return "";

        while (true) {
            String registration = Integer.toString(minDigitsFactor + random.nextInt(maxDigitsFactor));
            if (studentRepository.findByRegistration(registration).isEmpty())
                return registration;
        }
    }
}
