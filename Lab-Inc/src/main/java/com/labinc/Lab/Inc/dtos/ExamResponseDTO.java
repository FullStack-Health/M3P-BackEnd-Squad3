package com.labinc.Lab.Inc.dtos;

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
public class ExamResponseDTO {

    @Schema(description = " Exam examName")
    private Long id;

    @Schema(description = " Exam examName")
    private String examName;

    @Schema(description = " Exam examDate")
    private LocalDate examDate;

    @Schema(description = " Exam examTime")
    private LocalTime examTime;

    @Schema(description = " Exam examType")
    private String examType;

    @Schema(description = " Exam lab")
    private String lab;

    @Schema(description = " Exam docUrl")
    private String docUrl;

    @Schema(description = " Exam result")
    private String result;

    @Schema(description = " Exam patientId")
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
