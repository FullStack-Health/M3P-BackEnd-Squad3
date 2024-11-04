package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.AppointmentRequestDTO;
import com.labinc.Lab.Inc.dtos.AppointmentResponseDTO;
import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.entities.Patient;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    public Appointment toAppointment(AppointmentRequestDTO dto, Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setReason(dto.getReason());
        appointment.setConsultDate(dto.getConsultDate());
        appointment.setConsultTime(dto.getConsultTime());
        appointment.setProblemDescrip(dto.getProblemDescrip());
        appointment.setPrescMed(dto.getPrescMed());
        appointment.setDosagesPrec(dto.getDosagesPrec());
        appointment.setPatient(patient);
        return appointment;
    }

    public AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        Patient patientNameAndId = appointment.getPatient();
        dto.setFullName(patientNameAndId.getFullName());
        dto.setId(patientNameAndId.getId());
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setReason(appointment.getReason());
        dto.setConsultDate(appointment.getConsultDate());
        dto.setConsultTime(appointment.getConsultTime());
        dto.setProblemDescrip(appointment.getProblemDescrip());
        dto.setPrescMed(appointment.getPrescMed());
        dto.setDosagesPrec(appointment.getDosagesPrec());
        return dto;
    }

    public void updateAppointmentFromDTO(Appointment appointment, AppointmentRequestDTO dto) {
        appointment.setReason(dto.getReason());
        appointment.setConsultDate(dto.getConsultDate());
        appointment.setConsultTime(dto.getConsultTime());
        appointment.setProblemDescrip(dto.getProblemDescrip());
        appointment.setPrescMed(dto.getPrescMed());
        appointment.setDosagesPrec(dto.getDosagesPrec());
    }

}