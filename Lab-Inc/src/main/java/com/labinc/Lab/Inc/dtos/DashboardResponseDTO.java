package com.labinc.Lab.Inc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private Integer countPatients;

    private Integer countAppointments;

    private Integer countExams;

    private Integer countUsers;
}
