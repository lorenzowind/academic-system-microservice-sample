package com.studentService.studentService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.studentService.studentService.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
}