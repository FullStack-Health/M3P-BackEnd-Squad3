package com.labinc.Lab.Inc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Column(nullable = false)
    private AllowedRoles roleName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String fullName;

    @Email
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false)
    private String phone;


    public String getPasswordMasked() {
        if (password == null || password.length() <= 4) {
            return password; // Retorna a senha original se for nula ou menor ou igual a 4 caracteres
        }
        // Mostra os 4 primeiros caracteres e substitui o restante por '*'
        return password.substring(0, 4) + "*".repeat(password.length() - 4);
    }

    @Column(nullable = false, length = 255)
    private String password;

}

