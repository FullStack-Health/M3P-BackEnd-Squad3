package com.labinc.Lab.Inc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column(nullable = false, length = 64)
    private String fullName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String rg;

    @Column(nullable = false)
    private String maritalStatus;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String placeOfBirth;

    @Column(nullable = false)
    private String emergencyContact;

    private String listOfAllergies;
    private String listCare;
    private String healthInsurance;
    private String healthInsuranceNumber;
    private LocalDate healthInsuranceVal;

    @Column(nullable = false)
    private String zipcode;
    private String street;
    private String addressNumber;
    private String neighborhood;
    private String city;
    private String state;
    private String complement;
    private String referencePoint;
}