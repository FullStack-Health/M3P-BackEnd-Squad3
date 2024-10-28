package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.entities.Patient;

public class PatientMapper {

    public static void updatePatientFromDTO(PatientRequestDTO dto, Patient patient) {
        patient.setFullName(dto.getFullName());
        patient.setGender(dto.getGender());
        patient.setBirthDate(dto.getBirthDate());
        patient.setCpf(dto.getCpf());
        patient.setRg(dto.getRg());
        patient.setMaritalStatus(dto.getMaritalStatus());

        if (dto.getPhone() != null) {
            patient.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null) {
            patient.setEmail(dto.getEmail());
        }
        if (dto.getPlaceOfBirth() != null) {
            patient.setPlaceOfBirth(dto.getPlaceOfBirth());
        }
        if (dto.getEmergencyContact() != null) {
            patient.setEmergencyContact(dto.getEmergencyContact());
        }
        if (dto.getListOfAllergies() != null) {
            patient.setListOfAllergies(dto.getListOfAllergies());
        }
        if (dto.getListCare() != null) {
            patient.setListCare(dto.getListCare());
        }
        if (dto.getHealthInsurance() != null) {
            patient.setHealthInsurance(dto.getHealthInsurance());
        }
        if (dto.getHealthInsuranceNumber() != null) {
            patient.setHealthInsuranceNumber(dto.getHealthInsuranceNumber());
        }
        if (dto.getHealthInsuranceVal() != null) {
            patient.setHealthInsuranceVal(dto.getHealthInsuranceVal());
        }

        if (dto.getZipcode() != null) {
            patient.setZipcode(dto.getZipcode());
        }
        if (dto.getStreet() != null) {
            patient.setStreet(dto.getStreet());
        }
        if (dto.getAddressNumber() != null) {
            patient.setAddressNumber(dto.getAddressNumber());
        }
        if (dto.getNeighborhood() != null) {
            patient.setNeighborhood(dto.getNeighborhood());
        }
        if (dto.getCity() != null) {
            patient.setCity(dto.getCity());
        }
        if (dto.getState() != null) {
            patient.setState(dto.getState());
        }
        if (dto.getComplement() != null) {
            patient.setComplement(dto.getComplement());
        }
        if (dto.getReferencePoint() != null) {
            patient.setReferencePoint(dto.getReferencePoint());
        }
    }
}
