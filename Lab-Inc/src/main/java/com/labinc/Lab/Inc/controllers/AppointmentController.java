package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.AppointmentRequestDTO;
import com.labinc.Lab.Inc.dtos.AppointmentResponseDTO;
import com.labinc.Lab.Inc.mappers.AppointmentMapper;
import com.labinc.Lab.Inc.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointment", description = "Endpoints para gerenciamento de consultas")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @PostMapping
    @Operation(summary = "Registra uma nova consulta", description = "Registra uma nova consulta com base nos dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<AppointmentResponseDTO> registerAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws BadRequestException {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.registerAppointment(appointmentRequestDTO);
        return new ResponseEntity<>(appointmentResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma consulta existente", description = "Atualiza os dados de uma consulta existente com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
    })
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@Parameter(description = "ID da consulta", required = true, example = "1")
                                                                        @PathVariable Long id,
                                                                        @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws BadRequestException {
        AppointmentResponseDTO updateAppointment = appointmentService.updateAppointment(id, appointmentRequestDTO);
        return ResponseEntity.ok(updateAppointment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma consulta existente", description = "Deleta uma consulta existente com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consulta deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    public ResponseEntity<Void> deleteAppointment(@Parameter(description = "ID da consulta", required = true, example = "1") @PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém uma consulta por ID", description = "Obtém os dados de uma consulta com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta recuperada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@Parameter(description = "ID da consulta", required = true, example = "1") @PathVariable("id") Long pathId) {
        logger.info("Entering getAppointment with pathId: {}", pathId);

        AppointmentResponseDTO appointmentResponseDTO = appointmentService.getAppointment(pathId);
        return ResponseEntity.ok(appointmentResponseDTO);
    }


    @GetMapping("/patient/{id}")
    @Operation(summary = "Obtém todas as consultas de um paciente", description = "Obtém uma lista paginada de todas as consultas de um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultas listadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Nenhuma consulta encontrada")
    })
    public ResponseEntity<Page<AppointmentResponseDTO>> getAllAppointments(
            @Parameter(description = "ID do paciente", required = true, example = "1") @PathVariable("id") Long patientId,
            @Parameter(description = "Razão da consulta", example = "Rotina") @RequestParam(value = "reason", required = false) String reason,
            @PageableDefault(size = 5, sort = "consultDate", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("Getting appointments for patientId: {} with reason: {} and pageable: {}", patientId, reason, pageable);

        Page<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByPatientIdAndReason(patientId, reason, pageable);
        return ResponseEntity.ok(appointments);
    }

}
