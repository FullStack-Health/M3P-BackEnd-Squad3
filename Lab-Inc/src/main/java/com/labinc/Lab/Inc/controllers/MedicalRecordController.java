package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.MedicalRecordResponseDTO;
import com.labinc.Lab.Inc.services.MedicalRecordService;
import com.labinc.Lab.Inc.services.UserToPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medical-record")
@Tag(name = "Medical Record", description = "Endpoint para gerenciamento de prontuários médicos")
public class MedicalRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private UserToPatientService userToPatientService;

    @GetMapping("/{id}")
    @Operation(summary = "Obtém o prontuário médico de um paciente", description = "Obtém o prontuário médico de um paciente com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário médico recuperado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Prontuário médico não encontrado")
    })
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecord(@Parameter(description = "ID do prontuário médico", required = true, example = "1") @PathVariable("id") Long pathId, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = getUserIdFromAuthentication(auth);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (auth.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.getAuthority().equals("SCOPE_ADMIN") ||
                        grantedAuthority.getAuthority().equals("SCOPE_MEDICO"))) {
            MedicalRecordResponseDTO medicalRecord = medicalRecordService.getPatientMedicalRecord(pathId);
            return ResponseEntity.ok(medicalRecord);
        }

        Long patientId = userToPatientService.findPatientIdByUserId(userId);
        if (patientId != null && patientId.equals(pathId)) {
            MedicalRecordResponseDTO medicalRecord = medicalRecordService.getPatientMedicalRecord(pathId);
            return ResponseEntity.ok(medicalRecord);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            logger.error("Authentication or principal is null");
            return null;
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userIdString = jwt.getClaim("sub");
        logger.info("Extracted user ID string from JWT: {}", userIdString);

        if (userIdString == null || userIdString.isEmpty()) {
            logger.error("User ID is null or empty");
            return null;
        }

        try {
            return Long.parseLong(userIdString);
        } catch (NumberFormatException e) {
            logger.error("Invalid Long string: {}", userIdString, e);
            return null;
        }

    }
}
