package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByPatient_Id(Long id);
}
