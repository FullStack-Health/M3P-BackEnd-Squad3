package com.labinc.Lab.Inc.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.labinc.Lab.Inc.dtos.DashboardResponseDTO;
import com.labinc.Lab.Inc.repositories.AppointmentRepository;
import com.labinc.Lab.Inc.repositories.ExamRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    private DashboardService dashboardService;

    private Jwt mockJwt;

    @BeforeEach
    void setup() {
        mockJwt = mock(Jwt.class);

        lenient().when(jwtDecoder.decode(anyString())).thenReturn(mockJwt);

        lenient().when(patientRepository.count()).thenReturn(50L);
        lenient().when(appointmentRepository.count()).thenReturn(30L);
        lenient().when(examRepository.count()).thenReturn(20L);
        lenient().when(userRepository.count()).thenReturn(10L);
    }

    @Test
    void testGetDashboardStats_AsAdmin() {
        when(mockJwt.getClaim("scope")).thenReturn("SCOPE_ADMIN");

        DashboardResponseDTO result = dashboardService.getDashboardStats("Bearer test-token");

        assertNotNull(result);
        assertEquals(50L, result.getCountPatients());
        assertEquals(30L, result.getCountAppointments());
        assertEquals(20L, result.getCountExams());
        assertEquals(10L, result.getCountUsers());

        verify(patientRepository).count();
        verify(appointmentRepository).count();
        verify(examRepository).count();
        verify(userRepository).count();
    }

    @Test
    void testGetDashboardStats_AsNonAdmin() {
        when(mockJwt.getClaim("scope")).thenReturn("SCOPE_USER");

        DashboardResponseDTO result = dashboardService.getDashboardStats("Bearer test-token");

        assertNotNull(result);
        assertEquals(50L, result.getCountPatients());
        assertEquals(30L, result.getCountAppointments());
        assertEquals(20L, result.getCountExams());
        assertNull(result.getCountUsers());

        verify(patientRepository).count();
        verify(appointmentRepository).count();
        verify(examRepository).count();
        verify(userRepository, never()).count();
    }

    @Test
    void testGetDashboardStats_InvalidToken() {
        when(jwtDecoder.decode(anyString())).thenThrow(new RuntimeException("Invalid token"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                dashboardService.getDashboardStats("Bearer invalid-token")
        );
        assertEquals("Invalid token", exception.getMessage());
    }
}
