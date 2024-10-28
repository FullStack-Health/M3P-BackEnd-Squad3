package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.DashboardResponseDTO;
import com.labinc.Lab.Inc.repositories.AppointmentRepository;
import com.labinc.Lab.Inc.repositories.ExamRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
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
    @Autowired
    private JwtDecoder jwtDecoder;

    public DashboardResponseDTO getDashboardStats(String token) {

        DashboardResponseDTO dashboardResponseDTO = new DashboardResponseDTO();

        dashboardResponseDTO.setCountPatients(patientRepository.count());
        dashboardResponseDTO.setCountAppointments(appointmentRepository.count());
        dashboardResponseDTO.setCountExams(examRepository.count());

        Jwt tokenDecodificado = jwtDecoder.decode(token.split(" ")[1]);

        if ("ADMIN".equals(tokenDecodificado.getClaim("scope"))) {
        dashboardResponseDTO.setCountUsers(userRepository.count());
        } else {
            dashboardResponseDTO.setCountUsers(null);
        }

        return dashboardResponseDTO;
    }
}
