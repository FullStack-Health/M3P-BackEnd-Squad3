package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequestDTO {

    @Schema(description = " Exam examName")
    @NotBlank(message = "O nome do exame é obrigatório")
    @Size(min = 8, max = 64, message = "O Nome do exame deve ter entre 8 e 64 caracteres")
    private String examName;

    @Schema(description = "Exam examDate")
    @NotNull(message = "A data do exame é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate examDate;

    @Schema(description = "Exam examTime")
    @NotNull(message = "A Hora do exame é obrigatória")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime examTime;

    @Schema(description = "Exam examType")
    @Size(min = 4, max = 32, message = "O tipo do exame deve ter entre 4 e 32 caracteres")
    private String examType;

    @Schema(description = "Exam lab")
    @Size(min = 4, max = 32, message = "O laboratório deve ter entre 4 e 32 caracteres")
    private String lab;

    @Schema(description = "Exam docUrl")
    private String docUrl;

    @Schema(description = "Exam result")
    @Size(min = 16, max = 1024, message = "O resultado do exame deve ter entre 16 e 1024 caracteres")
    private String result;

    @Schema(description = "Exam patientId")
    private Long patientId;
}
