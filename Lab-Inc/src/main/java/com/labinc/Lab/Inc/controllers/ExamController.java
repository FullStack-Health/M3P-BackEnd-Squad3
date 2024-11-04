package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.ExamRequestDTO;
import com.labinc.Lab.Inc.dtos.ExamResponseDTO;
import com.labinc.Lab.Inc.services.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/exams")
@Tag(name = "Exam", description = "Endpoints para gerenciamento de exames")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo exame", description = "Cria um novo exame com base nos dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<ExamResponseDTO> newExam(@Valid @RequestBody ExamRequestDTO examRequestDTO){
        ExamResponseDTO examResponseDTO = examService.newExam(examRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(examResponseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(examResponseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém um exame por ID", description = "Obtém os dados de um exame com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame recuperado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    public ResponseEntity<ExamResponseDTO> examById(@Parameter(description = "ID do exame", required = true, example = "1") @PathVariable Long id){
        ExamResponseDTO examResponseDTO = examService.examById(id);
        return ResponseEntity.ok(examResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Obtém todos os exames", description = "Obtém uma lista paginada de todos os exames")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exames listados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")

    })
    public ResponseEntity<Page<ExamResponseDTO>> listExams(Pageable pageable){
        Page<ExamResponseDTO> examResponseDTOPage = examService.listExams(pageable);
        return ResponseEntity.ok(examResponseDTOPage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um exame existente", description = "Atualiza os dados de um exame existente com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    public ResponseEntity<?> updateExam(@Parameter(description = "ID do exame", required = true, example = "1") @PathVariable Long id, @Valid @RequestBody ExamRequestDTO examRequestDTO){
        ExamResponseDTO examUpdated = examService.updateExam(id, examRequestDTO);
        return ResponseEntity.ok(examUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um exame existente", description = "Deleta um exame existente com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    public ResponseEntity<Void> deleteExam(@Parameter(description = "ID do exame", required = true, example = "1") @PathVariable Long id){
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }


}
