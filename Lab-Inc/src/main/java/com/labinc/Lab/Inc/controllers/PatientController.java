package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.dtos.PatientResponseDTO;
import com.labinc.Lab.Inc.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Endpoints para gerenciamento de pacientes")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo paciente", description = "Cria um novo paciente e retorna as informações do paciente cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados, CPF, RG ou Email já cadastrados")
    })
    public ResponseEntity<PatientResponseDTO> addPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.newPatient(patientRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientResponseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(patientResponseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca paciente por ID", description = "Retorna as informações do paciente correspondente ao ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<PatientResponseDTO> patientById(@Parameter(description = "ID do paciente", required = true) @PathVariable Long id) {
        PatientResponseDTO patientResponseDTO = patientService.patientById(id);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Lista pacientes", description = "Retorna uma lista paginada de pacientes com base nos critérios de pesquisa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Page<PatientResponseDTO>> listPatients(
            @Parameter(description = "Nome do paciente para pesquisa") @RequestParam(required = false) String fullName,
            @Parameter(description = "Telefone do paciente para pesquisa") @RequestParam(required = false) String phone,
            @Parameter(description = "Email do paciente para pesquisa") @RequestParam(required = false) String email,
            @Parameter(description = "Id do paciente para pesquisa") @RequestParam(required = false) Long id,
            @Parameter(description = "Número da página para paginação") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página para paginação") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<PatientResponseDTO> patientResponseDTO = patientService.listPatients(fullName, phone, email, id, pageable);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza paciente", description = "Atualiza as informações de um paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")

    })
    public ResponseEntity<?> updatePatient(@Parameter(description = "ID do paciente a ser atualizado", required = true) @PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientUpdated = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok(patientUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta paciente", description = "Remove um paciente pelo ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<Void> deletePatient(@Parameter(description = "ID do paciente a ser deletado", required = true) @PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
