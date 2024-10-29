package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.MedicalRecordResponseDTO;
import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.entities.Exam;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.mappers.MedicalRecordMapper;
import com.labinc.Lab.Inc.repositories.AppointmentRepository;
import com.labinc.Lab.Inc.repositories.ExamRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    public MedicalRecordResponseDTO getPatientMedicalRecord(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Patient not found"));

        List<Exam> exams = examRepository.findByPatient_Id(id);
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(id);

        return medicalRecordMapper.toDto(patient, exams, appointments);
    }

    public List<MedicalRecordResponseDTO> getAllPatientsMedicalRecords() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(patient -> {
                    List<Exam> exams = examRepository.findByPatient_Id(patient.getId());
                    List<Appointment> appointments = appointmentRepository.findByPatient_Id(patient.getId());
                    return medicalRecordMapper.toDto(patient, exams, appointments);
                })
                .collect(Collectors.toList());
    }


}
