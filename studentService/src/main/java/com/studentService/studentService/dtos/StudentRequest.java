package com.studentService.studentService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String id;
    private String name;
    private int CPF;
    private String endereco;
    private int numeroMatricula;
}