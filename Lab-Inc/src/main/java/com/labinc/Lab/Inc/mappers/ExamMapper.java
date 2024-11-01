package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.ExamRequestDTO;
import com.labinc.Lab.Inc.dtos.ExamResponseDTO;
import com.labinc.Lab.Inc.entities.Exam;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {

    public static void updateExamFromDTO(ExamRequestDTO dto, Exam exam) {
        exam.setExamName(dto.getExamName());
        exam.setExamDate(dto.getExamDate());
        exam.setExamTime(dto.getExamTime());
        exam.setExamType(dto.getExamType());
        exam.setLab(dto.getLab());
        exam.setDocUrl(dto.getDocUrl());
        exam.setResult(dto.getResult());
    }

    public ExamResponseDTO toResponseDTO(Exam exam) {
        ExamResponseDTO dto = new ExamResponseDTO();
        dto.setExamId(exam.getExamId());
        dto.setExamName(exam.getExamName());
        dto.setExamDate(exam.getExamDate());
        dto.setExamTime(exam.getExamTime());
        dto.setExamType(exam.getExamType());
        dto.setLab(exam.getLab());
        dto.setDocUrl(exam.getDocUrl());
        dto.setResult(exam.getResult());
        return dto;
    }
}
