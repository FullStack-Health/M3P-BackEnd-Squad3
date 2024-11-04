package com.labinc.Lab.Inc.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informações sobre o usuário")
public class User implements UserDetails {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Nome do papel permitido para o usuário", example = "SCOPE_PACIENTE")
    private AllowedRoles roleName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Schema(description = "ID do usuário", example = "1")
    private Long userId;

    @Column(nullable = false, length = 255)
    @Schema(description = "Nome completo do usuário", example = "Carlos Henrique Silva")
    private String fullName;

    @Email
    @Column(nullable = false, unique = true, length = 255)
    @Schema(description = "E-mail do usuário", example = "carlos@teste.com")
    private String email;

    @Column
    @Schema(description = "Data de nascimento do usuário", example = "21/05/1990")
    private LocalDate birthdate;

    @Column(unique = true, length = 14)
    @Schema(description = "CPF do usuário", example = "027.889.456-12")
    private String cpf;

    @Column
    @Schema(description = "Telefone do usuário", example = "(48) 9 9856-2345")
    private String phone;

    public String getPasswordMasked(String password) {
        if (password == null || password.length() <= 4) {
            return password;
        }
        return password.substring(0, 4) + "**";
    }

    @Column(nullable = false, length = 255)
    @Schema(description = "Senha do usuário encriptada", example = "$2a$10$E2j9bH2A...")
    private String password;

    @Schema(description = "Senha mascarada do usuário", example = "1234**")
    private String passwordMasked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(roleName);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
