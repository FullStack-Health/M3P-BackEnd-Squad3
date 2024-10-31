package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.MedicalRecordResponseDTO;
import com.labinc.Lab.Inc.services.MedicalRecordService;
import com.labinc.Lab.Inc.services.UserToPatientService;
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
public class MedicalRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private UserToPatientService userToPatientService;

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecord(
            @PathVariable("id") Long pathId, HttpServletRequest request) {

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
