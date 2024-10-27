package com.labinc.Lab.Inc.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {
    @NotNull(message = "Patient id is required")
    private Long id;

    @NotBlank(message = "Reason is required")
    @Size(min = 8, max = 64, message = "Reason must be between 8 and 64 characters")
    private String reason;

    @NotNull(message = "Consult date is required")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate consultDate;

    @NotNull(message = "Consult time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime consultTime;

    @NotBlank(message = "Problem description is required")
    @Size(min = 16, max = 1024, message = "Problem description must be between 16 and 1024 characters")
    private String problemDescrip;

    private String prescMed;

    @Size(min = 16, max = 256, message = "Dosages description must not exceed 256 characters")
    private String dosagesPrec;
}
