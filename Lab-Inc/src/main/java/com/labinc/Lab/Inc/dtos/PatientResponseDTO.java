package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {

    @Schema(description = "Patient ID")
    private Long id;

    @Schema(description = " Patient fullName")
    private String fullName;

    @Schema(description = "Patient gender")
    private String gender;

    @Schema(description = "Patient birthDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Schema(description = "Patient cpf")
    private String cpf;

    @Schema(description = "Patient rg")
    private String rg;

    @Schema(description = "Patient maritalStatus")
    private String maritalStatus;

    @Schema(description = "Patient phone")
    private String phone;

    @Schema(description = "Patient email")
    private String email;

    @Schema(description = "Patient nationality")
    private String placeOfBirth;

    @Schema(description = "Patient emergencyContact")
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate healthInsuranceVal;

    @Schema(description = "Patient zipcode")
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
    private String complement;

    @Schema(description = "Patient referencePoint")
    private String referencePoint;

}

