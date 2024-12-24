package com.academicsys.academicservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.academicsys.academicservice.model.Subject;

public interface SubjectRepository extends MongoRepository<Subject, String> {
}
