package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados de resposta para o prontuário médico do paciente")
public class MedicalRecordResponseDTO {

    @Schema(description = "ID do paciente", example = "1")
    private Long id;

    @Schema(description = "Nome completo do paciente", example = "João da Silva")
    private String fullName;

    @Schema(description = "Gênero do paciente", example = "Masculino")
    private String gender;

    @Schema(description = "Data de nascimento do paciente", example = "15/10/1980")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Schema(description = "CPF do paciente", example = "000.000.000-00")
    private String cpf;

    @Schema(description = "RG do paciente", example = "123456789")
    private String rg;

    @Schema(description = "Estado civil do paciente", example = "Casado")
    private String maritalStatus;

    @Schema(description = "Telefone do paciente", example = "(48) 9 9999-9999")
    private String phone;

    @Schema(description = "Email do paciente", example = "joao.silva@example.com")
    private String email;

    @Schema(description = "Nacionalidade do paciente", example = "Brasileiro")
    private String placeOfBirth;

    @Schema(description = "Contato de emergência do paciente", example = "(48) 9 8888-7777")
    private String emergencyContact;

    @Schema(description = "Alergias do paciente", example = "Nenhuma")
    private String listOfAllergies;

    @Schema(description = "Cuidados especiais do paciente", example = "Nenhum")
    private String listCare;

    @Schema(description = "Plano de saúde do paciente", example = "Unimed")
    private String healthInsurance;

    @Schema(description = "Número do plano de saúde", example = "123456789")
    private String healthInsuranceNumber;

    @Schema(description = "Validade do plano de saúde", example = "31/12/2025")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate healthInsuranceVal;

    @Schema(description = "CEP do paciente", example = "88000-000")
    private String zipcode;

    @Schema(description = "Rua do paciente", example = "Rua das Flores")
    private String street;

    @Schema(description = "Número do endereço do paciente", example = "123")
    private String addressNumber;

    @Schema(description = "Bairro do paciente", example = "Centro")
    private String neighborhood;

    @Schema(description = "Cidade do paciente", example = "Florianópolis")
    private String city;

    @Schema(description = "Estado do paciente", example = "Santa Catarina")
    private String state;

    @Schema(description = "Complemento do endereço do paciente", example = "Apto 101")
    private String complement;

    @Schema(description = "Ponto de referência do endereço do paciente", example = "Próximo ao shopping")
    private String referencePoint;

    @Schema(description = "Lista de consultas do paciente")
    private List<AppointmentResponseDTO> appointments;

    @Schema(description = "Lista de exames do paciente")
    private List<ExamResponseDTO> exams;
}
