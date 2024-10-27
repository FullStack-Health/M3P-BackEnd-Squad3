package com.labinc.Lab.Inc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private Integer patientsNumbers;

    private Integer appointmentNumber;

    private Integer examsNumber;

    private Integer usersNumber;
}
