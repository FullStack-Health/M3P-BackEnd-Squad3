package com.labinc.Lab.Inc.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name="appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informações sobre a consulta")
public class Appointment {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    @Schema(description = "Paciente associado à consulta", requiredMode = Schema.RequiredMode.REQUIRED)
    private Patient patient;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da consulta", example = "1")
    private Long appointmentId;

    @Column(nullable = false, length = 64)
    @Schema(description = "Razão da consulta", requiredMode = Schema.RequiredMode.REQUIRED, example = "Rotina")
    private String reason;

    @Column(nullable = false)
    @Schema(description = "Data da consulta", requiredMode = Schema.RequiredMode.REQUIRED, example = "15/10/2024")
    private LocalDate consultDate;

    @Column(nullable = false)
    @Schema(description = "Hora da consulta", requiredMode = Schema.RequiredMode.REQUIRED, example = "14:30")
    private LocalTime consultTime;

    @Column(nullable = false, length = 1024)
    @Schema(description = "Descrição do problema", requiredMode = Schema.RequiredMode.REQUIRED, example = "Dor de cabeça persistente")
    private String problemDescrip;

    @Column
    @Schema(description = "Medicamentos prescritos", example = "Paracetamol")
    private String prescMed;

    @Column(length = 256)
    @Schema(description = "Dosagens e precauções", example = "1 comprimido a cada 8 horas")
    private String dosagesPrec;

}
