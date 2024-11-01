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
public class ExamResponseDTO {

    @Schema(description = " Exam examId")
    private Long examId;

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
    private Long id;

    @Schema(description = " Exam patientFullName")
    private String fullName;


    public ExamResponseDTO(Exam exam){
        examId = exam.getExamId();
        examName = exam.getExamName();
        examDate = exam.getExamDate();
        examTime = exam.getExamTime();
        examType = exam.getExamType();
        lab = exam.getLab();
        docUrl = exam.getDocUrl();
        result = exam.getResult();
        id = exam.getPatient().getId();
        fullName = exam.getPatient().getFullName();
    }

}
