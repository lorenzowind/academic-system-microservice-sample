package com.academicsys.academicservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "enrollment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Enrollment {
    @Id
    private String id;
    private String registration;
    private String code;
    private String classroom;
}
