package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.ExamRequestDTO;
import com.labinc.Lab.Inc.dtos.ExamResponseDTO;
import com.labinc.Lab.Inc.entities.Exam;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.repositories.ExamRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private ExamService examService;

    private ExamRequestDTO examRequestDTO;
    private Exam exam;
    private Patient patient;

    @BeforeEach
    void setup() {
        patient = new Patient();
        patient.setId(1L);

        exam = new Exam();
        exam.setExamId(1L);
        exam.setPatient(patient);

        examRequestDTO = new ExamRequestDTO();
        examRequestDTO.setId(patient.getId());
        examRequestDTO.setExamName("Blood Test");
        examRequestDTO.setExamDate(LocalDate.now());
        examRequestDTO.setExamTime(LocalTime.now());
        examRequestDTO.setExamType("Lab");
        examRequestDTO.setLab("LabInc");
        examRequestDTO.setDocUrl("http://labinc.com/");
        examRequestDTO.setResult("Normal");

        lenient().when(examRepository.save(any(Exam.class))).thenReturn(exam);
    }

    @Test
    void testNewExam_Success() {
        when(patientRepository.findById(examRequestDTO.getId())).thenReturn(Optional.of(patient));

        ExamResponseDTO response = examService.newExam(examRequestDTO);

        assertNotNull(response);
        verify(patientRepository).findById(examRequestDTO.getId());
        verify(examRepository).save(any(Exam.class));
    }

    @Test
    void testNewExam_PatientNotFound() {
        when(patientRepository.findById(examRequestDTO.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> examService.newExam(examRequestDTO));
        verify(patientRepository).findById(examRequestDTO.getId());
        verify(examRepository, never()).save(any(Exam.class));
    }

    @Test
    void testNewExam_MissingPatientId() {
        examRequestDTO.setId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> examService.newExam(examRequestDTO));
        assertEquals("ID do paciente é obrigatório", exception.getMessage());
    }

    @Test
    void testExamById_Success() {
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        ExamResponseDTO response = examService.examById(1L);

        assertNotNull(response);
        verify(examRepository).findById(1L);
    }

    @Test
    void testExamById_NotFound() {
        when(examRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> examService.examById(1L));
        verify(examRepository).findById(1L);
    }

    @Test
    void testListExams_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Exam> examPage = new PageImpl<>(List.of(exam));
        when(examRepository.findAll(pageable)).thenReturn(examPage);

        Page<ExamResponseDTO> response = examService.listExams(pageable);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        verify(examRepository).findAll(pageable);
    }

    @Test
    void testUpdateExam_Success() {
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        ExamResponseDTO response = examService.updateExam(1L, examRequestDTO);

        assertNotNull(response);
        verify(examRepository).findById(1L);
        verify(examRepository).save(any(Exam.class));
    }

    @Test
    void testUpdateExam_NotFound() {
        when(examRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> examService.updateExam(1L, examRequestDTO));
        verify(examRepository).findById(1L);
        verify(examRepository, never()).save(any(Exam.class));
    }

    @Test
    void testDeleteExam_Success() {
        when(examRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> examService.deleteExam(1L));
        verify(examRepository).deleteById(1L);
    }

    @Test
    void testDeleteExam_NotFound() {
        when(examRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> examService.deleteExam(1L));
        verify(examRepository, never()).deleteById(any());
    }
}
