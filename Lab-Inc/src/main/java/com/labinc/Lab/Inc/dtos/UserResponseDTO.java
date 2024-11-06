package com.labinc.Lab.Inc.dtos;

import com.labinc.Lab.Inc.entities.AllowedRoles;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados de resposta para um usuário")
public class UserResponseDTO {

    @Schema(description = "Nome do papel do usuário", example = "SCOPE_ADMIN")
    private AllowedRoles roleName;

    @Schema(description = "ID do usuário", example = "1")
    private Long userId;

    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String fullName;

    @Schema(description = "Email do usuário", example = "joao.silva@example.com")
    private String email;

    @Schema(description = "Data de nascimento do usuário", example = "01/01/1990")
    private LocalDate birthDate;

    @Schema(description = "CPF do usuário", example = "000.000.000-00")
    private String cpf;

    @Schema(description = "Telefone do usuário", example = "(48) 9 9999-9999")
    private String phone;

    @Schema(description = "Senha do usuário", example = "senha123")
    private String password;

}
