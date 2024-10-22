package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.dtos.PatientResponseDTO;
import com.labinc.Lab.Inc.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> addPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        // Chamando o serviço para criar um novo paciente e obter o PatientResponseDTO
        PatientResponseDTO patientResponseDTO = patientService.newPatient(patientRequestDTO);

        // Construindo a URI do novo recurso criado com base no ID retornado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientResponseDTO.getId())
                .toUri();

        // Retornando a resposta com o status 201 Created e o objeto PatientResponseDTO no corpo
        return ResponseEntity.created(uri).body(patientResponseDTO);
    }
}
