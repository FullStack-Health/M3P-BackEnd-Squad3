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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private Patient patient;
    private List<Exam> exams;
    private List<Appointment> appointments;

    @BeforeEach
    void setup() {
        patient = new Patient();
        patient.setId(1L);

        exams = new ArrayList<>();
        exams.add(new Exam()); // Add test exams as needed

        appointments = new ArrayList<>();
        appointments.add(new Appointment()); // Add test appointments as needed
    }

    @Test
    void testGetPatientMedicalRecord_Success() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(examRepository.findByPatient_Id(1L)).thenReturn(exams);
        when(appointmentRepository.findByPatient_Id(1L)).thenReturn(appointments);
        when(medicalRecordMapper.toDto(patient, exams, appointments)).thenReturn(new MedicalRecordResponseDTO());

        MedicalRecordResponseDTO response = medicalRecordService.getPatientMedicalRecord(1L);

        assertNotNull(response);
        verify(patientRepository).findById(1L);
        verify(examRepository).findByPatient_Id(1L);
        verify(appointmentRepository).findByPatient_Id(1L);
        verify(medicalRecordMapper).toDto(patient, exams, appointments);
    }

    @Test
    void testGetPatientMedicalRecord_PatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> medicalRecordService.getPatientMedicalRecord(1L));
        verify(patientRepository).findById(1L);
        verify(examRepository, never()).findByPatient_Id(any());
        verify(appointmentRepository, never()).findByPatient_Id(any());
    }

    @Test
    void testGetAllPatientsMedicalRecords_Success() {
        List<Patient> patients = List.of(patient);
        when(patientRepository.findAll()).thenReturn(patients);
        when(examRepository.findByPatient_Id(patient.getId())).thenReturn(exams);
        when(appointmentRepository.findByPatient_Id(patient.getId())).thenReturn(appointments);
        when(medicalRecordMapper.toDto(patient, exams, appointments)).thenReturn(new MedicalRecordResponseDTO());

        List<MedicalRecordResponseDTO> response = medicalRecordService.getAllPatientsMedicalRecords();

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(patientRepository).findAll();
        verify(examRepository).findByPatient_Id(patient.getId());
        verify(appointmentRepository).findByPatient_Id(patient.getId());
        verify(medicalRecordMapper).toDto(patient, exams, appointments);
    }
}
