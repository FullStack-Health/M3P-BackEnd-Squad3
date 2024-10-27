package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.DashboardResponseDTO;
import com.labinc.Lab.Inc.repositories.AppointmentRepository;
import com.labinc.Lab.Inc.repositories.ExamRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private UserRepository userRepository;

    public DashboardResponseDTO getDashboardStats() {

        DashboardResponseDTO dashboardResponseDTO = new DashboardResponseDTO();

        dashboardResponseDTO.setCountPatients(patientRepository.count());
        dashboardResponseDTO.setCountAppointments(appointmentRepository.count());
        dashboardResponseDTO.setCountExams(examRepository.count());

        //TODO: Implementar junto com o security (Para o atributo "Quantidade de usuários": retornar "null" quando o perfil for diferente de ROLE_ADMIN)
        dashboardResponseDTO.setCountUsers(userRepository.count());

        return dashboardResponseDTO;
    }
}
