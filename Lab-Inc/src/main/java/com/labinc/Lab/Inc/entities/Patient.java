package com.labinc.Lab.Inc.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
@Schema(description = "Informações sobre o paciente")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    @Schema(description = "ID do paciente", example = "1")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @Schema(description = "Usuário associado ao paciente")
    private User user;

    @Column(nullable = false, length = 64)
    @Schema(description = "Nome completo do paciente", example = "Carlos Henrique Silva")
    private String fullName;

    @Column(nullable = false)
    @Schema(description = "Gênero do paciente", example = "Masculino")
    private String gender;

    @Column(nullable = false)
    @Schema(description = "Data de nascimento do paciente", example = "1990-05-21")
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    @Schema(description = "CPF do paciente", example = "027.889.456-12")
    private String cpf;

    @Column(nullable = false, unique = true)
    @Schema(description = "RG do paciente", example = "2356987")
    private String rg;

    @Column(nullable = false)
    @Schema(description = "Estado civil do paciente", example = "Solteiro")
    private String maritalStatus;

    @Column(nullable = false)
    @Schema(description = "Telefone do paciente", example = "(48) 9 9856-2345")
    private String phone;

    @Column(nullable = false, unique = true)
    @Schema(description = "E-mail do paciente", example = "carlos@example.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Naturalidade do paciente", example = "Florianópolis")
    private String placeOfBirth;

    @Column(nullable = false)
    @Schema(description = "Contato de emergência", example = "(48) 9 9856-1234")
    private String emergencyContact;

    @Schema(description = "Lista de alergias do paciente", example = "Nenhuma")
    private String listOfAllergies;

    @Schema(description = "Cuidados específicos do paciente", example = "Nenhum")
    private String listCare;

    @Schema(description = "Plano de saúde do paciente", example = "Unimed")
    private String healthInsurance;

    @Schema(description = "Número do plano de saúde", example = "123456789")
    private String healthInsuranceNumber;

    @Schema(description = "Validade do plano de saúde", example = "31/12/2025")
    private LocalDate healthInsuranceVal;

    @Column(nullable = false)
    @Schema(description = "CEP do endereço", example = "88010-000")
    private String zipcode;

    @Schema(description = "Rua do endereço", example = "Rua das Flores")
    private String street;

    @Schema(description = "Número do endereço", example = "123")
    private String addressNumber;

    @Schema(description = "Bairro do endereço", example = "Centro")
    private String neighborhood;

    @Schema(description = "Cidade do endereço", example = "Florianópolis")
    private String city;

    @Schema(description = "Estado do endereço", example = "Santa Catarina")
    private String state;

    @Schema(description = "Complemento do endereço", example = "Apto 45")
    private String complement;

    @Schema(description = "Ponto de referência do endereço", example = "Próximo ao shopping")
    private String referencePoint;
}