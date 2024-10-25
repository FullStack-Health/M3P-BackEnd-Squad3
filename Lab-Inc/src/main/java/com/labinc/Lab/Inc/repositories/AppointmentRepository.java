package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient_Id(Long id);

    boolean existsByPatient_Id(Long id);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId AND " +
            "(:reason IS NULL OR LOWER(a.reason) LIKE LOWER(CONCAT('%', :reason, '%')))")
    Page<Appointment> findByPatientIdAndReasonContaining(
            @Param("patientId") Long patientId,
            @Param("reason") String reason,
            Pageable pageable);
}

