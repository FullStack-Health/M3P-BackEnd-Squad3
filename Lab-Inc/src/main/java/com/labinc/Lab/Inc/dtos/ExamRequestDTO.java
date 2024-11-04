package com.labinc.Lab.Inc.dtos;

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
@Schema(description = "Dados de requisição para criação de um exame")
public class ExamRequestDTO {

    @Schema(description = "Nome do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "Exame de Sangue")
    @NotBlank(message = "O nome do exame é obrigatório")
    @Size(min = 8, max = 64, message = "O Nome do exame deve ter entre 8 e 64 caracteres")
    private String examName;

    @Schema(description = "Data do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "15/10/2024")
    @NotNull(message = "A data do exame é obrigatória")
    private LocalDate examDate;

    @Schema(description = "Hora do exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "14:30")
    @NotNull(message = "A Hora do exame é obrigatória")
    private LocalTime examTime;

    @Schema(description = "Tipo do exame", example = "Laboratorial")
    @Size(min = 4, max = 32, message = "O tipo do exame deve ter entre 4 e 32 caracteres")
    private String examType;

    @Schema(description = "Laboratório do exame", example = "Laboratório XYZ")
    @Size(min = 4, max = 32, message = "O laboratório deve ter entre 4 e 32 caracteres")
    private String lab;

    @Schema(description = "URL do documento do exame", example = "http://example.com/doc.pdf")
    private String docUrl;

    @Schema(description = "Resultado do exame", example = "Negativo")
    @Size(min = 16, max = 1024, message = "O resultado do exame deve ter entre 16 e 1024 caracteres")
    private String result;

    @Schema(description = "ID do paciente associado ao exame", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;
}
