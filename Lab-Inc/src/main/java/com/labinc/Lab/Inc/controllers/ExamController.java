package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.ExamRequestDTO;
import com.labinc.Lab.Inc.dtos.ExamResponseDTO;
import com.labinc.Lab.Inc.services.ExamService;
import com.labinc.Lab.Inc.services.PatientService;
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
public class ExamController {

    private final ExamService examService;
    private final PatientService patientService;

    public ExamController(ExamService examService, PatientService patientService) {
        this.examService = examService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<ExamResponseDTO> newExam(@Valid @RequestBody ExamRequestDTO examRequestDTO){
        ExamResponseDTO examResponseDTO = examService.newExam(examRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(examResponseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(examResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> examById(@PathVariable Long id){
        ExamResponseDTO examResponseDTO = examService.examById(id);
        return ResponseEntity.ok(examResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ExamResponseDTO>> listExams(Pageable pageable){
        Page<ExamResponseDTO> examResponseDTOPage = examService.listExams(pageable);
        return ResponseEntity.ok(examResponseDTOPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExam(@PathVariable Long id, @Valid @RequestBody ExamRequestDTO examRequestDTO){
        ExamResponseDTO examUpdated = examService.updateExam(id, examRequestDTO);
        return ResponseEntity.ok(examUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id){
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }


}
