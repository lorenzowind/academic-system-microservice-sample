package com.academicsys.academicservice.repository;

import com.academicsys.academicservice.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {
    List<Enrollment> findByRegistration(String registration);
}
