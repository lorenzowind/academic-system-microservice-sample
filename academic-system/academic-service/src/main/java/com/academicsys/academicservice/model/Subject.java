package com.academicsys.academicservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "subject")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Subject {
    @Id
    private String id;
    private String code;
    private String name;
    private String period; // A, B, C, D, E, F, G
    private String classroom; // Numerical code
}
