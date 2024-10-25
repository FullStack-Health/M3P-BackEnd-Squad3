package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.AppointmentRequestDTO;
import com.labinc.Lab.Inc.dtos.AppointmentResponseDTO;
import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.mappers.AppointmentMapper;
import com.labinc.Lab.Inc.services.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> registerAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws BadRequestException {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.registerAppointment(appointmentRequestDTO);
        return new ResponseEntity<>(appointmentResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable Long id,
                                                                    @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws BadRequestException {
        AppointmentResponseDTO updateAppointment = appointmentService.updateAppointment(id, appointmentRequestDTO);
        return ResponseEntity.ok(updateAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable("id") Long pathId) {
        logger.info("Entering getAppointment with pathId: {}", pathId);

        AppointmentResponseDTO appointmentResponseDTO = appointmentService.getAppointment(pathId);
        return ResponseEntity.ok(appointmentResponseDTO);
    }


    @GetMapping("/patient/{id}")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAllAppointments(
            @PathVariable("id") Long patientId,
            @RequestParam(value = "reason", required = false) String reason,
            @PageableDefault(size = 5, sort = "consultDate", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("Getting appointments for patientId: {} with reason: {} and pageable: {}", patientId, reason, pageable);

        Page<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByPatientIdAndReason(patientId, reason, pageable);
        return ResponseEntity.ok(appointments);
    }



}
