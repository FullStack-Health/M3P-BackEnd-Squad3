package com.labinc.Lab.Inc.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exams")
@Schema(description = "Informações sobre o Exame")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do exame", example = "1")
    private Long examId;

    @Column(nullable = false, length = 64)
    @Schema(description = "Nome do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "Exame de Sangue")
    private String examName;

    @Column(nullable = false)
    @Schema(description = "Data do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "15/11/2024")
    private LocalDate examDate;

    @Column(nullable = false)
    @Schema(description = "Hora do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "14:30")
    private LocalTime examTime;

    @Column(nullable = false, length = 32)
    @Schema(description = "Tipo do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "Laboratorial")
    private String examType;

    @Column(nullable = false, length = 32)
    @Schema(description = "Laboratório do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "Laboratório XYZ")
    private String lab;

    @Schema(description = "URL do documento do exame", example = "http://example.com/doc.pdf")
    private String docUrl;

    @Column(length = 1024)
    @Schema(description = "Resultado do exame", example = "Negativo")
    private String result;

    @Schema(description = "Paciente associado ao exame", requiredMode = Schema.RequiredMode.REQUIRED)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;
}
