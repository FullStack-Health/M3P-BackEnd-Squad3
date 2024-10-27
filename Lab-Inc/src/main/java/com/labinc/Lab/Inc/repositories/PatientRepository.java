package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);

    boolean existsByEmail(String email);
}
