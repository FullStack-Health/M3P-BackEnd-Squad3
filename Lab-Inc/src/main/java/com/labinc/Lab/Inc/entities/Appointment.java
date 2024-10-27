package com.labinc.Lab.Inc.entities;

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
public class Appointment {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @Column(nullable = false, length = 64)
    private String reason;

    @Column(nullable = false)
    private LocalDate consultDate;

    @Column(nullable = false)
    private LocalTime consultTime;

    @Column(nullable = false, length = 1024)
    private String problemDescrip;

    @Column
    private String prescMed;

    @Column(length = 256)
    private String dosagesPrec;

}
