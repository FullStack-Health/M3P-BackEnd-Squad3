package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.MedicalRecordResponseDTO;
import com.labinc.Lab.Inc.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients/record")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> getPatientMedicalRecord(@PathVariable Long id) {
        MedicalRecordResponseDTO medicalRecord = medicalRecordService.getPatientMedicalRecord(id);
        return ResponseEntity.ok(medicalRecord);
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordResponseDTO>> getAllPatientsMedicalRecords() {
        List<MedicalRecordResponseDTO> medicalRecords = medicalRecordService.getAllPatientsMedicalRecords();
        return ResponseEntity.ok(medicalRecords);
    }
}
