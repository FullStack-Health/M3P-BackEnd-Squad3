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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados de requisição para criação de uma consulta")
public class AppointmentRequestDTO {

    @Schema(description = "ID do paciente", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Patient id is required")
    private Long id;

    @Schema(description = "Razão da consulta", requiredMode = Schema.RequiredMode.REQUIRED, example = "Rotina")
    @NotBlank(message = "Reason is required")
    @Size(min = 8, max = 64, message = "Reason must be between 8 and 64 characters")
    private String reason;

    @Schema(description = "Data da consulta", requiredMode = Schema.RequiredMode.REQUIRED, example = "15/10/2024")
    @NotNull(message = "Consult date is required")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate consultDate;

    @Schema(description = "Hora da consulta", requiredMode = Schema.RequiredMode.REQUIRED, example = "14:30")
    @NotNull(message = "Consult time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime consultTime;

    @Schema(description = "Descrição do problema", requiredMode = Schema.RequiredMode.REQUIRED, example = "Dor de cabeça persistente")
    @NotBlank(message = "Problem description is required")
    @Size(min = 16, max = 1024, message = "Problem description must be between 16 and 1024 characters")
    private String problemDescrip;

    @Schema(description = "Medicamentos prescritos", example = "Paracetamol")
    private String prescMed;

    @Schema(description = "Dosagens e precauções", example = "1 comprimido a cada 8 horas")
    @Size(min = 16, max = 256, message = "Dosages description must not exceed 256 characters")
    private String dosagesPrec;
}
