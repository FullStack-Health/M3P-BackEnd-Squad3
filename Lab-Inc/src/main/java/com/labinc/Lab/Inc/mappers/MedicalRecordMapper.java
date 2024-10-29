package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.MedicalRecordResponseDTO;
import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.entities.Exam;
import com.labinc.Lab.Inc.entities.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordMapper {

    private final ExamMapper examMapper;
    private final AppointmentMapper appointmentMapper;
    private final PatientMapper patientMapper;

    public MedicalRecordMapper(ExamMapper examMapper, AppointmentMapper appointmentMapper, PatientMapper patientMapper) {
        this.examMapper = examMapper;
        this.appointmentMapper = appointmentMapper;
        this.patientMapper = patientMapper;
    }

    public MedicalRecordResponseDTO toDto(Patient patient, List<Exam> exams, List<Appointment> appointments) {
        MedicalRecordResponseDTO dto = new MedicalRecordResponseDTO();

        dto.setPatient(patientMapper.toResponseDTO(patient));

        if (exams != null) {
            dto.setExams(exams.stream().map(examMapper::toResponseDTO).collect(Collectors.toList()));
        }
        if (appointments != null) {
            dto.setAppointments(appointments.stream().map(appointmentMapper::toResponseDTO).collect(Collectors.toList()));
        }

        return dto;
    }

}