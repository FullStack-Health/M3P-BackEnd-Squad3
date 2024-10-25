package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.AppointmentRequestDTO;
import com.labinc.Lab.Inc.dtos.AppointmentResponseDTO;
import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.mappers.AppointmentMapper;
import com.labinc.Lab.Inc.repositories.AppointmentRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentService(PatientRepository patientRepository, AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

        public AppointmentResponseDTO registerAppointment(AppointmentRequestDTO appointmentRequestDTO) throws BadRequestException {
            Patient patient =
                    patientRepository.findById(appointmentRequestDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Patient not found"));

            if (appointmentRequestDTO.getReason() == null || appointmentRequestDTO.getReason().isEmpty()) {
                throw new BadRequestException("reason is mandatory");
            }

            if (appointmentRequestDTO.getConsultDate() == null) {
                throw new BadRequestException("consultDate is mandatory");
            }

            if (appointmentRequestDTO.getConsultTime() == null) {
                throw new BadRequestException("consultTime is mandatory");
            }

            if (appointmentRequestDTO.getId() == null) {
                throw new BadRequestException("patient id is mandatory");
            }

            if (appointmentRequestDTO.getProblemDescrip() == null || appointmentRequestDTO.getProblemDescrip().isEmpty()) {
                throw new BadRequestException("problemDescrip is mandatory");
            }

            Appointment appointment = appointmentMapper.toAppointment(appointmentRequestDTO, patient);

            Appointment savedAppointment = appointmentRepository.save(appointment);

            return appointmentMapper.toResponseDTO(savedAppointment);
        }

        public AppointmentResponseDTO getAppointment(Long id) {
            Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                    "Appointment not found"));
            return appointmentMapper.toResponseDTO(appointment);
        }

        public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO appointmentRequestDTO) throws BadRequestException {
            Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                    "Appointment not found"));

            if (!appointment.getPatient().getId().equals(appointmentRequestDTO.getId())) {
                throw new BadRequestException("Patient ID in the request does not match the Patient ID in the existing appointment");
            }

            if (appointmentRequestDTO.getReason() == null || appointmentRequestDTO.getReason().isEmpty()) {
                throw new BadRequestException("reason is mandatory");
            }

            if (appointmentRequestDTO.getConsultDate() == null) {
                throw new BadRequestException("consultDate is mandatory");
            }

            if (appointmentRequestDTO.getConsultTime() == null) {
                throw new BadRequestException("consultTime is mandatory");
            }

            if (appointmentRequestDTO.getId() == null) {
                throw new BadRequestException("patient id is mandatory");
            }

            if (appointmentRequestDTO.getProblemDescrip() == null || appointmentRequestDTO.getProblemDescrip().isEmpty()) {
                throw new BadRequestException("problemDescrip is mandatory");
            }

            appointmentMapper.updateAppointmentFromDTO(appointment, appointmentRequestDTO);

            Appointment savedAppointment = appointmentRepository.save(appointment);

            return appointmentMapper.toResponseDTO(savedAppointment);
        }

        public void deleteAppointment(Long id) {
            if (!appointmentRepository.existsById(id)) {
                throw new EntityNotFoundException("Appointment not found");
            }
            appointmentRepository.deleteById(id);
        }

    public Page<AppointmentResponseDTO> getAppointmentsByPatientIdAndReason(Long patientId,
                                                                            String reason,
                                                                            Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByPatientIdAndReasonContaining(patientId, reason, pageable);
        return appointments.map(appointmentMapper::toResponseDTO);
    }


    public Appointment getAppointmentById(Long appointmentId) throws ChangeSetPersister.NotFoundException {
            return appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Appointment not found"));
        }
    }

