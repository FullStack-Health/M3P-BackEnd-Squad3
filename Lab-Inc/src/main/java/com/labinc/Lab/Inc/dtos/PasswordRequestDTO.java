package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de requisição para atualização de senha do usuário")
public class PasswordRequestDTO {
    @Schema(description = "Senha do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "novaSenha123")
    @NotBlank(message = "A senha é obrigatória")
    @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres")
    private String password;
}
