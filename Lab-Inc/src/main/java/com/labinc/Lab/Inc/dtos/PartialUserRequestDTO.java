package com.labinc.Lab.Inc.dtos;

import com.labinc.Lab.Inc.entities.AllowedRoles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartialUserRequestDTO {
    @Schema(description = "User email")
    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
    private String email;

    @Schema(description = "User password")
    @NotBlank(message = "A senha é obrigatória")
    @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres")
    private String password;

    @Schema(description = "User roleName")
    @NotNull(message = "O perfil é obrigatório")
    private AllowedRoles roleName;
}
