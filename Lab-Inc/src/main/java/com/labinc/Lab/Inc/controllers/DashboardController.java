package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.DashboardResponseDTO;
import com.labinc.Lab.Inc.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Endpoint para gerenciamento do dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/stats")
    @Operation(summary = "Obtém as estatísticas do dashboard", description = "Obtém a quantidade de pacientes, consultas, exames e usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
    })
    public ResponseEntity<DashboardResponseDTO> getDashboardStats(@RequestHeader("Authorization") String token) {
        logger.info("Entering getDashboardStats");

        DashboardResponseDTO dashboardResponseDTO = dashboardService.getDashboardStats(token);

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