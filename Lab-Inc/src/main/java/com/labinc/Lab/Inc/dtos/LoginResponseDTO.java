package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de resposta para login de usuário")
public class LoginResponseDTO {
    @Schema(description = "Valor do JSON Web Token", requiredMode = Schema.RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Tempo após o qual o token não é mais válido", requiredMode = Schema.RequiredMode.REQUIRED, example = "3600")
    private Long expirationTime;
}