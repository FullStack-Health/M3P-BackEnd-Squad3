package com.labinc.Lab.Inc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {
        private Long appointmentId;
        private String reason;
        private LocalDate consultDate;
        private LocalTime consultTime;
        private String problemDescrip;
        private String prescMed;
        private String dosagesPrec;
}
