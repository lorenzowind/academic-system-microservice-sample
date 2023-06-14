package com.studentService.studentService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.studentService.studentService.dtos.StudentRequest;
import com.studentService.studentService.dtos.StudentResponse;
import com.studentService.studentService.model.Student;
import com.studentService.studentService.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    public void createStudent(StudentRequest studentRequest) {
        Student student = Student.builder()
                .id(studentRequest.getId())
                .name(studentRequest.getName())
                .CPF(studentRequest.getCPF())
                .endereco(studentRequest.getEndereco())
                .numeroMatricula(studentRequest.getNumeroMatricula())
                .build();

        studentRepository.save(student);

        log.info("StudentService.createStudent: Student saved successfully : student id = {}", student.getId());
    }

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(this::mapToStudentResponse)
                .collect(Collectors.toList());
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .CPF(student.getCPF())
                .endereco(student.getEndereco())
                .numeroMatricula(student.getNumeroMatricula())
                .build();
    }
    
}
