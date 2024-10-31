package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserToPatientServiceImpl implements UserToPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Long findPatientIdByUserId(Long userId) {

        Patient patient = patientRepository.findByUser_UserId(userId);
        return patient != null ? patient.getId() : null;
    }
}
