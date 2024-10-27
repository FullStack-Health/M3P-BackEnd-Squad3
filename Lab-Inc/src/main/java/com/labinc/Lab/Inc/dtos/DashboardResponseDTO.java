package com.labinc.Lab.Inc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private Long countPatients;

    private Long countAppointments;

    private Long countExams;

    private Long countUsers;
}
