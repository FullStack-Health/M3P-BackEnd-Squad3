package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.ExamRequestDTO;
import com.labinc.Lab.Inc.entities.Exam;

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
}
