package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labinc.Lab.Inc.entities.Patient;
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

    @Schema(description = "Nome completo do paciente", example = "Carlos Henrique Silva")
    private String fullName;

    @Schema(description = "Gênero do paciente", example = "Masculino")
    private String gender;

    @Schema(description = "Data de nascimento do paciente", example = "21/05/1990")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Schema(description = "CPF do paciente", example = "000.000.000-00")
    private String cpf;

    @Schema(description = "RG do paciente", example = "2356987")
    private String rg;

    @Schema(description = "Estado civil do paciente", example = "Solteiro")
    private String maritalStatus;

    @Schema(description = "Telefone do paciente", example = "(48) 9 9856-2345")
    private String phone;

    @Schema(description = "E-mail do paciente", example = "carlos@teste.com")
    private String email;

    @Schema(description = "Naturalidade do paciente", example = "Brasileiro")
    private String placeOfBirth;

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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate healthInsuranceVal;

    @Schema(description = "CEP do endereço", example = "99999-999")
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

    public PatientResponseDTO(Patient patient) {
        this.id = patient.getId();
        this.fullName = patient.getFullName();
        this.gender = patient.getGender();
        this.birthDate = patient.getBirthDate();
        this.cpf = patient.getCpf();
        this.rg = patient.getRg();
        this.maritalStatus = patient.getMaritalStatus();
        this.phone = patient.getPhone();
        this.email = patient.getEmail();
        this.placeOfBirth = patient.getPlaceOfBirth();
        this.emergencyContact = patient.getEmergencyContact();
        this.listOfAllergies = patient.getListOfAllergies();
        this.listCare = patient.getListCare();
        this.healthInsurance = patient.getHealthInsurance();
        this.healthInsuranceNumber = patient.getHealthInsuranceNumber();
        this.healthInsuranceVal = patient.getHealthInsuranceVal();
        this.zipcode = patient.getZipcode();
        this.street = patient.getStreet();
        this.addressNumber = patient.getAddressNumber();
        this.neighborhood = patient.getNeighborhood();
        this.city = patient.getCity();
        this.state = patient.getState();
        this.complement = patient.getComplement();
        this.referencePoint = patient.getReferencePoint();
    }
}

