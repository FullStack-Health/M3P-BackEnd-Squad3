package com.labinc.Lab.Inc.dtos;

import com.labinc.Lab.Inc.entities.AllowedRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private AllowedRoles roleName;
    private Long userId;
    private String fullName;
    private String email;
    private LocalDate birthdate;
    private String cpf;
    private String phone;
    private String password;

}
