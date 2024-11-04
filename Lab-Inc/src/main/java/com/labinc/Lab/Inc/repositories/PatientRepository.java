package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);

    boolean existsByEmail(String email);

    Page<Patient> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE REPLACE(REPLACE(REPLACE(p.phone, '-', ''), ' ', ''), '(', '') LIKE %:phone%")
    Page<Patient> findByPhoneContaining(String phone, Pageable pageable);

    Page<Patient> findByEmailIgnoreCase(String email, Pageable pageable);

    Patient findByUser_UserId (Long userId);

    Page<Patient> findById (Long id, Pageable pageable);
}
