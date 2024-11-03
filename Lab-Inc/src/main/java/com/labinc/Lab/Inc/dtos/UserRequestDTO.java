package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.entities.AllowedRolesDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados de requisição para criação de um usuário")
public class UserRequestDTO {

    @Schema(description = "Nome do papel do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "SCOPE_ADMIN")
    @JsonDeserialize(using = AllowedRolesDeserializer.class)
    @NotNull(message = "roleName is required")
    private AllowedRoles roleName;

    @Schema(description = "ID do usuário", example = "1")
    private Long userId;

    @Schema(description = "Nome completo do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "João da Silva")
    @NotBlank(message = "fullName is required")
    private String fullName;

    @Schema(description = "Email do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "joao.silva@example.com")
    @Email
    @NotBlank(message = "email is required")
    private String email;

    @Schema(description = "Data de nascimento do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "1990-01-01")
    @NotNull(message = "birthdate is required")
    private LocalDate birthdate;

    @Schema(description = "CPF do usuário", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255, example = "000.000.000-00")
    @NotNull
    @Size(max = 255)
    @JsonFormat(pattern = "000.000.000-00")
    private String cpf;

    @Schema(description = "Senha do usuário", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255, example = "senha123")
    @NotBlank
    @Size(max = 255)
    private String password;

    @Schema(description = "Telefone do usuário", requiredMode = Schema.RequiredMode.REQUIRED, example = "(48) 9 9999-9999")
    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}", message = "O telefone deve estar no formato (99) 9 9999-9999")
    private String phone;
}
