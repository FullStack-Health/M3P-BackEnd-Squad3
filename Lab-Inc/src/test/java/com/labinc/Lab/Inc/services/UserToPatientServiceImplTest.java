package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserToPatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private UserToPatientServiceImpl userToPatientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setUserId(1L);

        patient = new Patient();
        patient.setId(1L);
        patient.setUser(user);
    }

    @Test
    void findPatientIdByUserId_Found() {
        Long userId = 1L;
        when(patientRepository.findByUser_UserId(userId)).thenReturn(patient);

        Long result = userToPatientService.findPatientIdByUserId(userId);

        assertEquals(patient.getId(), result);
        assertEquals(patient.getUser().getUserId(), userId);
    }

    @Test
    void findPatientIdByUserId_NotFound() {
        Long userId = 2L;
        when(patientRepository.findByUser_UserId(userId)).thenReturn(null);

        Long result = userToPatientService.findPatientIdByUserId(userId);

        assertNull(result);
        assertNotEquals(patient.getUser().getUserId(), userId);
    }
}
