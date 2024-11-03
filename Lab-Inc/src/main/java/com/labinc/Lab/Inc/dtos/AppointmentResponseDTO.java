package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados de resposta para uma consulta")
public class AppointmentResponseDTO {

        @Schema(description = "ID da consulta", example = "1")
        private Long appointmentId;

        @Schema(description = "Razão da consulta", example = "Rotina")
        private String reason;

        @Schema(description = "Data da consulta", example = "15/10/2024")
        private LocalDate consultDate;

        @Schema(description = "Hora da consulta", example = "14:30")
        private LocalTime consultTime;

        @Schema(description = "Descrição do problema", example = "Dor de cabeça persistente")
        private String problemDescrip;

        @Schema(description = "Medicamentos prescritos", example = "Paracetamol")
        private String prescMed;

        @Schema(description = "Dosagens e precauções", example = "1 comprimido a cada 8 horas")
        private String dosagesPrec;
}
