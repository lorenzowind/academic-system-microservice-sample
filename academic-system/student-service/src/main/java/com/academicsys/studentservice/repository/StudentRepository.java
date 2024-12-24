package com.academicsys.studentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academicsys.studentservice.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    //Optional<Student> findByIdentification(String identification);
    List<Student> findByNameLike(String name);
    Optional<Student> findByRegistration(String registration);
}