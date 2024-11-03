package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labinc.Lab.Inc.entities.Exam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de resposta para um exame")
public class ExamResponseDTO {

    @Schema(description = "ID do exame", example = "1")
    private Long id;

    @Schema(description = "Nome do exame", example = "Exame de Sangue")
    private String examName;

    @Schema(description = "Data do exame", example = "15/10/2024")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate examDate;

    @Schema(description = "Hora do exame", example = "14:30")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime examTime;

    @Schema(description = "Tipo do exame", example = "Laboratorial")
    private String examType;

    @Schema(description = "Laboratório do exame", example = "Laboratório XYZ")
    private String lab;

    @Schema(description = "URL do documento do exame", example = "http://example.com/doc.pdf")
    private String docUrl;

    @Schema(description = "Resultado do exame", example = "Negativo")
    private String result;

    @Schema(description = "ID do paciente associado ao exame", example = "1")
    private Long patientId;


    public ExamResponseDTO(Exam exam){
        id = exam.getId();
        examName = exam.getExamName();
        examDate = exam.getExamDate();
        examTime = exam.getExamTime();
        examType = exam.getExamType();
        lab = exam.getLab();
        docUrl = exam.getDocUrl();
        result = exam.getResult();
        patientId = exam.getPatient().getId();
    }

}
