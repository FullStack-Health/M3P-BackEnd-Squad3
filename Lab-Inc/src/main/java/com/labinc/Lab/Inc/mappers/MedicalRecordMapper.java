package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.MedicalRecordResponseDTO;
import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.entities.Exam;
import com.labinc.Lab.Inc.entities.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordMapper {

    private final ExamMapper examMapper;
    private final AppointmentMapper appointmentMapper;
    private final PatientMapper patientMapper;

    public MedicalRecordMapper(ExamMapper examMapper, AppointmentMapper appointmentMapper, PatientMapper patientMapper) {
        this.examMapper = examMapper;
        this.appointmentMapper = appointmentMapper;
        this.patientMapper = patientMapper;
    }

    public MedicalRecordResponseDTO toDto(Patient patient, List<Exam> exams, List<Appointment> appointments) {
        MedicalRecordResponseDTO dto = new MedicalRecordResponseDTO();

        dto.setId(patient.getId());
        dto.setFullName(patient.getFullName());
        dto.setGender(patient.getGender());
        dto.setBirthDate(patient.getBirthDate());
        dto.setCpf(patient.getCpf());
        dto.setRg(patient.getRg());
        dto.setMaritalStatus(patient.getMaritalStatus());
        dto.setPhone(patient.getPhone());
        dto.setEmail(patient.getEmail());
        dto.setPlaceOfBirth(patient.getPlaceOfBirth());
        dto.setEmergencyContact(patient.getEmergencyContact());
        dto.setListOfAllergies(patient.getListOfAllergies());
        dto.setListCare(patient.getListCare());
        dto.setHealthInsurance(patient.getHealthInsurance());
        dto.setHealthInsuranceNumber(patient.getHealthInsuranceNumber());
        dto.setHealthInsuranceVal(patient.getHealthInsuranceVal());
        dto.setZipcode(patient.getZipcode());
        dto.setStreet(patient.getStreet());
        dto.setAddressNumber(patient.getAddressNumber());
        dto.setNeighborhood(patient.getNeighborhood());
        dto.setCity(patient.getCity());
        dto.setState(patient.getState());
        dto.setComplement(patient.getComplement());
        dto.setReferencePoint(patient.getReferencePoint());

        if (exams != null) {
            dto.setExams(exams.stream().map(examMapper::toResponseDTO).collect(Collectors.toList()));
        }
        if (appointments != null) {
            dto.setAppointments(appointments.stream().map(appointmentMapper::toResponseDTO).collect(Collectors.toList()));
        }

        return dto;
    }

}