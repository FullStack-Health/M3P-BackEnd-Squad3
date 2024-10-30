package com.labinc.Lab.Inc.entities;

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
public class User implements UserDetails {

    @Enumerated(EnumType.STRING)
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

    @Column
    private LocalDate birthdate;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column
    private String phone;

    public String getPasswordMasked(String password) {
        if (password == null || password.length() <= 4) {
            return password;
        }
        return password.substring(0, 4) + "**";
    }

    @Column(nullable = false, length = 255)
    private String password;

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
