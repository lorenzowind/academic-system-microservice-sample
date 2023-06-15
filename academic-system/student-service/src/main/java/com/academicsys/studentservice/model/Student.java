package com.academicsys.studentservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "student")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Student {
    @Id
    private String id;
    private String name;
    private String identification;
    private String address;
    private String registration;
}