package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.DashboardResponseDTO;
import com.labinc.Lab.Inc.services.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/stats")
    public ResponseEntity<DashboardResponseDTO> getDashboardStats() {
        logger.info("Entering getDashboardStats");

        DashboardResponseDTO dashboardResponseDTO = dashboardService.getDashboardStats();

        logger.info("""
            Results ->
            countPatients: {}
            countAppointments: {}
            countExams: {}
            countUsers: {}
            """,
                dashboardResponseDTO.getCountPatients(),
                dashboardResponseDTO.getCountAppointments(),
                dashboardResponseDTO.getCountExams(),
                dashboardResponseDTO.getCountUsers()
        );

        return ResponseEntity.ok(dashboardResponseDTO);
    }
}