package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.ExamRequestDTO;
import com.labinc.Lab.Inc.dtos.ExamResponseDTO;
import com.labinc.Lab.Inc.entities.Exam;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.repositories.ExamRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final PatientRepository patientRepository;

    public ExamService(ExamRepository examRepository, PatientRepository patientRepository) {
        this.examRepository = examRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public ExamResponseDTO newExam(ExamRequestDTO examRequestDTO){
        if (examRequestDTO.getPatientId() == null){
            throw new IllegalArgumentException("ID do paciente é obrigatório");
        }

        Patient patient = patientRepository.findById(examRequestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID: " + examRequestDTO.getPatientId() + " não encontrado."));

        Exam exam = new Exam();
        exam.setExamName(examRequestDTO.getExamName());
        exam.setExamDate(examRequestDTO.getExamDate());
        exam.setExamTime(examRequestDTO.getExamTime());
        exam.setExamType(examRequestDTO.getExamType());
        exam.setLab(examRequestDTO.getLab());
        exam.setDocUrl(examRequestDTO.getDocUrl());
        exam.setResult(examRequestDTO.getResult());
        exam.setPatient(patient);

        exam =examRepository.save(exam);
        return new ExamResponseDTO(exam);

    }

    @Transactional(readOnly = true)
    public ExamResponseDTO examById(Long id){
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Exame não encontrado com o id " + id)
        );
        return new ExamResponseDTO(exam);
    }

    @Transactional(readOnly = true)
    public Page<ExamResponseDTO> listExams(Pageable pageable){

        //  TODO: Fazer Verificação Código401(Unauthorized)- Falha de autenticação.
        //  TODO: Criar  Parâmetros de query: nome do exame (opcional)

        Page<Exam> exams = examRepository.findAll(pageable);
        return exams.map(ExamResponseDTO::new);
    }

}
