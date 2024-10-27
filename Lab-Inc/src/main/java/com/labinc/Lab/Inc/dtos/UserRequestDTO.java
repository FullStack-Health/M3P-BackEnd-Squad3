package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.entities.AllowedRolesDeserializer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @JsonDeserialize(using = AllowedRolesDeserializer.class)
    @NotNull(message = "roleName is required")
    private AllowedRoles roleName;

    private Long userId;

    @NotBlank(message = "fullName is required")
    private String fullName;

    @Email
    @NotBlank(message = "email is required")
    private String email;

    @NotNull(message = "birthdate is required")
    private LocalDate birthdate;

    @NotNull
    @Size(max = 255)
    @JsonFormat(pattern = "000.000.000-00")
    private String cpf;

    @NotBlank
    @Size(max = 255)
    private String password;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d \\d{4}-\\d{4}", message = "O telefone deve estar no formato (99) 9 9999-9999")
    private String phone;
}
