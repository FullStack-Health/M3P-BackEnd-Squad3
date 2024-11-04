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
@Schema(description = "Dados de requisição para criação de um paciente")
public class PatientRequestDTO {
    @Schema(description = "Nome completo do paciente", example = "Carlos Henrique Silva")
    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 8, max = 64, message = "O Nome completo deve ter entre 8 e 64 caracteres")
    private String fullName;

    @Schema(description = "Gênero do paciente", example = "Masculino")
    @NotBlank(message = "O gênero é obrigatório")
    private String gender;

    @Schema(description = "Data de nascimento do paciente", example = "21/05/1990")
    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    private LocalDate birthDate;

    @Schema(description = "CPF do paciente", example = "000.000.000-00")
    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato 000.000.000-00")
    private String cpf;

    @Schema(description = "RG do paciente", example = "2356987")
    @NotBlank(message = "O RG é obrigatório")
    @Size(max = 20, message = "O RG deve ter no máximo 20 caracteres")
    private String rg;

    @Schema(description = "Estado civil do paciente", example = "Solteiro")
    @NotBlank(message = "O Estado Civil é obrigatório")
    private String maritalStatus;

    @Schema(description = "Telefone do paciente", example = "(48) 9 9856-2345")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d\\s\\d{4}-\\d{4}", message = "O telefone deve estar no formato (99) 9 9999-9999")
    private String phone;

    @Schema(description = "E-mail do paciente", example = "carlos@teste.com")
    @Email(message = "O email deve ser válido")
    private String email;

    @Schema(description = "Naturalidade do paciente", example = "Brasileiro")
    @NotBlank(message = "A naturalidade é obrigatória")
    @Size(min = 8, max = 64, message = "A naturalidade deve ter entre 8 e 64 caracteres")
    private String placeOfBirth;

    @Schema(description = "Contato de emergência", example = "(48) 9 9856-1234")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d\\s\\d{4}-\\d{4}", message = "O telefone deve estar no formato (99) 9 9999-9999")
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
    @Future(message = "A validade do plano de saúde deve estar no futuro")
    private LocalDate healthInsuranceVal;

    @Schema(description = "CEP do endereço", example = "99999-999")
    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 99999-999")
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
    private String complement ;

    @Schema(description = "Ponto de referência do endereço", example = "Próximo ao shopping")
    private String referencePoint;

}
