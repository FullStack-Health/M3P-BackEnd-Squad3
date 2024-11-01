package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {
    @Schema(description = "Patient fullName")
    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 8, max = 64, message = "O Nome completo deve ter entre 8 e 64 caracteres")
    private String fullName;

    @Schema(description = "Patient gender")
    @NotBlank(message = "O gênero é obrigatório")
    private String gender;

    @Schema(description = "Patient birthDate")
    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    private LocalDate birthDate;

    @Schema(description = "Patient cpf")
    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato 000.000.000-00")
    private String cpf;

    @Schema(description = "Patient rg")
    @NotBlank(message = "O RG é obrigatório")
    @Size(max = 20, message = "O RG deve ter no máximo 20 caracteres")
    private String rg;

    @Schema(description = "Patient maritalStatus")
    @NotBlank(message = "O Estado Civil é obrigatório")
    private String maritalStatus;

    @Schema(description = "Patient phone")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d\\s\\d{4}-\\d{4}", message = "O telefone deve estar no formato (99) 9 9999-9999")
    private String phone;

    @Schema(description = "Patient email")
    @Email(message = "O email deve ser válido")
    private String email;

    @Schema(description = "Patient nationality")
    @NotBlank(message = "A naturalidade é obrigatória")
    @Size(min = 8, max = 64, message = "A naturalidade deve ter entre 8 e 64 caracteres")
    private String placeOfBirth;

    @Schema(description = "Patient emergencyContact")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d\\s\\d{4}-\\d{4}", message = "O telefone deve estar no formato (99) 9 9999-9999")
    private String emergencyContact;

    @Schema(description = "Patient allergies")
    private String listOfAllergies;

    @Schema(description = "Patient specialCare")
    private String listCare;

    @Schema(description = "Patient healthInsurance")
    private String healthInsurance;

    @Schema(description = "Patient numInsurance")
    private String healthInsuranceNumber;

    @Schema(description = "Patient valInsurance")
    @Future(message = "A validade do plano de saúde deve estar no futuro")
    private LocalDate healthInsuranceVal;

    @Schema(description = "Patient zipcode")
    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
    private String zipcode;

    @Schema(description = "Patient street")
    private String street;

    @Schema(description = "Patient addressNumber")
    private String addressNumber;

    @Schema(description = "Patient neighborhood")
    private String neighborhood;

    @Schema(description = "Patient city")
    private String city;

    @Schema(description = "Patient state")
    private String state;

    @Schema(description = "Patient complement")
    private String complement ;

    @Schema(description = "Patient referencePoint")
    private String referencePoint;

}
