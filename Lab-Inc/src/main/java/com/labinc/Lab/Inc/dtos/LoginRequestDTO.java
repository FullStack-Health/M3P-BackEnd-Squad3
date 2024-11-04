package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de requisição para login de usuário")
public class LoginRequestDTO {
    @Schema(description = "Email do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "usuario@example.com")
    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
    private String email;

    @Schema(description = "Senha do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "senha123")
    @NotBlank(message = "A senha é obrigatória")
    @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres")
    private String password;
}