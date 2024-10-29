package com.labinc.Lab.Inc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordResponseDTO {
    private PatientResponseDTO patient;
    private List<AppointmentResponseDTO> appointments;
    private List<ExamResponseDTO> exams;
}
