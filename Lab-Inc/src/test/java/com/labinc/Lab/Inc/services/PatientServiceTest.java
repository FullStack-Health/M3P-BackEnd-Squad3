package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.dtos.PatientResponseDTO;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import com.labinc.Lab.Inc.services.exceptions.ResourceAlreadyExistsException;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    private PatientRequestDTO patientRequestDTO;
    private Patient patient;
    private User user;

    @BeforeEach
    void setup() {
        patientRequestDTO = new PatientRequestDTO();
        patientRequestDTO.setFullName("Cristian Yamamoto");
        patientRequestDTO.setCpf("12345678901");
        patientRequestDTO.setRg("RG123456");
        patientRequestDTO.setEmail("cristian_yamamoto@estudante.sesisenai.org.br");
        patientRequestDTO.setPhone("(99) 9 9999-9999");

        patient = new Patient();
        patient.setId(1L);
        patient.setFullName("Cristian Yamamoto");

        user = new User();
        user.setUserId(1L);
        user.setFullName("Cristian User");
        user.setCpf("12345678901");
    }

    @Test
    void testNewPatient_Success() {
        when(patientRepository.existsByCpf(patientRequestDTO.getCpf())).thenReturn(false);
        when(patientRepository.existsByRg(patientRequestDTO.getRg())).thenReturn(false);
        when(patientRepository.existsByEmail(patientRequestDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(patientRequestDTO.getCpf())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientResponseDTO response = patientService.newPatient(patientRequestDTO);

        assertNotNull(response);
        assertEquals("Cristian Yamamoto", response.getFullName());
        verify(patientRepository).existsByCpf(patientRequestDTO.getCpf());
        verify(patientRepository).existsByRg(patientRequestDTO.getRg());
        verify(patientRepository).existsByEmail(patientRequestDTO.getEmail());
        verify(userRepository).save(any(User.class));
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testNewPatient_CpfAlreadyExists() {
        when(patientRepository.existsByCpf(patientRequestDTO.getCpf())).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> patientService.newPatient(patientRequestDTO));

        assertEquals("O CPF já está cadastrado: " + patientRequestDTO.getCpf(), exception.getMessage());
        verify(patientRepository).existsByCpf(patientRequestDTO.getCpf());
        verify(patientRepository, never()).existsByRg(any());
        verify(patientRepository, never()).existsByEmail(any());
    }

    @Test
    void testPatientById_Success() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        PatientResponseDTO response = patientService.patientById(1L);

        assertNotNull(response);
        assertEquals("Cristian Yamamoto", response.getFullName());
        verify(patientRepository).findById(1L);
    }

    @Test
    void testPatientById_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> patientService.patientById(1L));

        assertEquals("Paciente não encontrado com o id 1", exception.getMessage());
        verify(patientRepository).findById(1L);
    }

    @Test
    void testListPatients_Success() {
        List<Patient> patients = new ArrayList<>();

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setFullName("Cristian Assis");

        patients.add(patient);
        patients.add(patient2);

        Page<Patient> patientPage = new PageImpl<>(patients);
        when(patientRepository.findAll(any(Pageable.class))).thenReturn(patientPage);

        Page<PatientResponseDTO> response = patientService.listPatients(null, null, null, null, Pageable.unpaged());

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals("Cristian Yamamoto", response.getContent().getFirst().getFullName());
        assertEquals("Cristian Assis", response.getContent().getLast().getFullName());
        verify(patientRepository).findAll(any(Pageable.class));
    }

    @Test
    void testUpdatePatient_Success() {
        PatientRequestDTO updateRequest = new PatientRequestDTO();
        updateRequest.setFullName("Cristian Assis");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientResponseDTO response = patientService.updatePatient(1L, updateRequest);

        assertNotNull(response);
        assertEquals("Cristian Assis", response.getFullName());
        verify(patientRepository).findById(1L);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testUpdatePatient_NotFound() {
        PatientRequestDTO updateRequest = new PatientRequestDTO();

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> patientService.updatePatient(1L, updateRequest));

        assertEquals("Paciente com ID: 1 não encontrado.", exception.getMessage());
        verify(patientRepository).findById(1L);
    }

    @Test
    void testDeletePatient_Success() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        patientService.deletePatient(1L);

        verify(patientRepository).existsById(1L);
        verify(patientRepository).deleteById(1L);
    }

    @Test
    void testDeletePatient_NotFound() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> patientService.deletePatient(1L));

        assertEquals("Paciente com ID: 1 não encontrado.", exception.getMessage());
        verify(patientRepository).existsById(1L);
        verify(patientRepository, never()).deleteById(any());
    }
}
