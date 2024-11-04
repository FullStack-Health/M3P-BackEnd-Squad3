package com.labinc.Lab.Inc.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.labinc.Lab.Inc.dtos.AppointmentRequestDTO;
import com.labinc.Lab.Inc.dtos.AppointmentResponseDTO;
import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.mappers.AppointmentMapper;
import com.labinc.Lab.Inc.repositories.AppointmentRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    private AppointmentRequestDTO appointmentRequestDTO;
    private Appointment appointment;
    private Patient patient;

    @BeforeEach
    void setup() {
        patient = new Patient();
        patient.setId(1L);

        appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setPatient(patient);

        appointmentRequestDTO = new AppointmentRequestDTO();
        appointmentRequestDTO.setId(patient.getId());
        appointmentRequestDTO.setReason("Checkup");
        appointmentRequestDTO.setConsultDate(LocalDate.now());
        appointmentRequestDTO.setConsultTime(LocalTime.now());
        appointmentRequestDTO.setProblemDescrip("General health check");

        lenient().when(appointmentMapper.toAppointment(any(), any())).thenReturn(appointment);
        lenient().when(appointmentMapper.toResponseDTO(any())).thenReturn(new AppointmentResponseDTO());
    }

    @Test
    void testRegisterAppointment_Success() throws BadRequestException {
        when(patientRepository.findById(appointmentRequestDTO.getId())).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(any())).thenReturn(appointment);

        AppointmentResponseDTO response = appointmentService.registerAppointment(appointmentRequestDTO);

        assertNotNull(response);
        verify(patientRepository).findById(appointmentRequestDTO.getId());
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void testRegisterAppointment_PatientNotFound() {
        when(patientRepository.findById(appointmentRequestDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.registerAppointment(appointmentRequestDTO));
        verify(patientRepository).findById(appointmentRequestDTO.getId());
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void testRegisterAppointment_MissingReason() {
        when(patientRepository.findById(appointmentRequestDTO.getId())).thenReturn(Optional.of(patient));
        appointmentRequestDTO.setReason(null);


        BadRequestException exception = assertThrows(BadRequestException.class, () -> appointmentService.registerAppointment(appointmentRequestDTO));
        assertEquals("reason is mandatory", exception.getMessage());
    }

    @Test
    void testRegisterAppointment_MissingConsultDate() {
        when(patientRepository.findById(appointmentRequestDTO.getId())).thenReturn(Optional.of(patient));
        appointmentRequestDTO.setConsultDate(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> appointmentService.registerAppointment(appointmentRequestDTO));
        assertEquals("consultDate is mandatory", exception.getMessage());
    }

    @Test
    void testRegisterAppointment_MissingConsulTime() {
        when(patientRepository.findById(appointmentRequestDTO.getId())).thenReturn(Optional.of(patient));
        appointmentRequestDTO.setConsultTime(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> appointmentService.registerAppointment(appointmentRequestDTO));
        assertEquals("consultTime is mandatory", exception.getMessage());
    }

    @Test
    void testRegisterAppointment_MissingProblemDescrip() {
        when(patientRepository.findById(appointmentRequestDTO.getId())).thenReturn(Optional.of(patient));
        appointmentRequestDTO.setProblemDescrip(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> appointmentService.registerAppointment(appointmentRequestDTO));
        assertEquals("problemDescrip is mandatory", exception.getMessage());
    }

    @Test
    void testGetAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        AppointmentResponseDTO response = appointmentService.getAppointment(1L);

        assertNotNull(response);
        verify(appointmentRepository).findById(1L);
    }

    @Test
    void testGetAppointment_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.getAppointment(1L));
        verify(appointmentRepository).findById(1L);
    }

    @Test
    void testUpdateAppointment_Success() throws BadRequestException {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponseDTO response = appointmentService.updateAppointment(1L, appointmentRequestDTO);

        assertNotNull(response);
        verify(appointmentRepository).findById(1L);
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void testDeleteAppointment_Success() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> appointmentService.deleteAppointment(1L));
        verify(appointmentRepository).deleteById(1L);
    }

    @Test
    void testDeleteAppointment_NotFound() {
        when(appointmentRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> appointmentService.deleteAppointment(1L));
        verify(appointmentRepository, never()).deleteById(any());
    }

    @Test
    void testGetAppointmentsByPatientIdAndReason_Success() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Appointment> appointmentPage = new PageImpl<>(List.of(appointment));
        when(appointmentRepository.findByPatientId(1L, pageable)).thenReturn(appointmentPage);

        Page<AppointmentResponseDTO> response = appointmentService.getAppointmentsByPatientIdAndReason(1L, "", pageable);

        assertNotNull(response);
        verify(appointmentRepository).findByPatientId(1L, pageable);
    }
}