package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados de resposta para o dashboard")
public class DashboardResponseDTO {

    @Schema(description = "Quantidade de pacientes", example = "150")
    private Long countPatients;

    @Schema(description = "Quantidade de consultas", example = "75")
    private Long countAppointments;

    @Schema(description = "Quantidade de exames", example = "200")
    private Long countExams;

    @Schema(description = "Quantidade de usuários", example = "50")
    private Long countUsers;
}
