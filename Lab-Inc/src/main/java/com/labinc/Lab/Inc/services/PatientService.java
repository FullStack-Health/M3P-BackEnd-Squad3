package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.dtos.PatientResponseDTO;
import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.mappers.PatientMapper;
import com.labinc.Lab.Inc.mappers.UserMapper;
import com.labinc.Lab.Inc.repositories.AppointmentRepository;
import com.labinc.Lab.Inc.repositories.ExamRepository;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import com.labinc.Lab.Inc.services.exceptions.ResourceAlreadyExistsException;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AppointmentRepository appointmentRepository;
    private final ExamRepository examRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, AppointmentRepository appointmentRepository, ExamRepository examRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.appointmentRepository = appointmentRepository;
        this.examRepository = examRepository;
    }

    @Transactional
    public PatientResponseDTO newPatient(PatientRequestDTO patientRequestDTO) {

        // Verifica se CPF já existe
        if (patientRepository.existsByCpf(patientRequestDTO.getCpf()) || userRepository.existsByCpf(patientRequestDTO.getCpf())) {
            throw new ResourceAlreadyExistsException("O CPF já está cadastrado: " + patientRequestDTO.getCpf());
        }

        // Verifica se RG já existe
        if (patientRepository.existsByRg(patientRequestDTO.getRg())) {
            throw new ResourceAlreadyExistsException("O RG já está cadastrado: " + patientRequestDTO.getRg());
        }

        // Verifica se Email já existe
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail()) || userRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("O Email já está cadastrado: " + patientRequestDTO.getEmail());
        }

        // Cria e salva o usuário do tipo "PACIENTE" correspondente ao paciente
        User user = new User();
        user.setFullName(patientRequestDTO.getFullName());
        user.setEmail(patientRequestDTO.getEmail());
        user.setBirthDate(patientRequestDTO.getBirthDate());
        user.setCpf(patientRequestDTO.getCpf());
        user.setPassword(passwordEncoder.encode(patientRequestDTO.getCpf())); // Configura a senha como o CPF encriptado
        user.setPasswordMasked(user.getPasswordMasked(patientRequestDTO.getCpf()));
        user.setPhone(patientRequestDTO.getPhone());
        user.setRoleName(AllowedRoles.SCOPE_PACIENTE); // Define o perfil como "PACIENTE"
        userRepository.save(user); // Salva o usuário

        Patient patient = new Patient();
        patient.setFullName(patientRequestDTO.getFullName());
        patient.setGender(patientRequestDTO.getGender());
        patient.setBirthDate(patientRequestDTO.getBirthDate());
        patient.setCpf(patientRequestDTO.getCpf());
        patient.setRg(patientRequestDTO.getRg());
        patient.setMaritalStatus(patientRequestDTO.getMaritalStatus());
        patient.setPhone(patientRequestDTO.getPhone());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setPlaceOfBirth(patientRequestDTO.getPlaceOfBirth());
        patient.setEmergencyContact(patientRequestDTO.getEmergencyContact());
        patient.setListOfAllergies(patientRequestDTO.getListOfAllergies());
        patient.setListCare(patientRequestDTO.getListCare());
        patient.setHealthInsurance(patientRequestDTO.getHealthInsurance());
        patient.setHealthInsuranceNumber(patientRequestDTO.getHealthInsuranceNumber());
        patient.setHealthInsuranceVal(patientRequestDTO.getHealthInsuranceVal());
        patient.setZipcode(patientRequestDTO.getZipcode());
        patient.setStreet(patientRequestDTO.getStreet());
        patient.setAddressNumber(patientRequestDTO.getAddressNumber());
        patient.setNeighborhood(patientRequestDTO.getNeighborhood());
        patient.setCity(patientRequestDTO.getCity());
        patient.setState(patientRequestDTO.getState());
        patient.setComplement(patientRequestDTO.getComplement());
        patient.setReferencePoint(patientRequestDTO.getReferencePoint());
        patient.setUser(user); // Associa o usuário ao paciente

        patient = patientRepository.save(patient);

        return new PatientResponseDTO(patient);
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO patientById(Long id) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente não encontrado com o id " + id)
        );
        return new PatientResponseDTO(patient);
    }

    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> listPatients(String fullName, String phone, String email, Long id, Pageable pageable) {

        Page<Patient> result;

        if (fullName != null && !fullName.isEmpty()) {
            result = patientRepository.findByFullNameContainingIgnoreCase(fullName, pageable);
        } else if (phone != null && !phone.isEmpty()) {
            String normalizedPhone = phone.replaceAll("[^\\d]", "");
            result = patientRepository.findByPhoneContaining(normalizedPhone, pageable);
        } else if (email != null && !email.isEmpty()) {
            result = patientRepository.findByEmailIgnoreCase(email, pageable);
        } else if (id != null) {
            result = patientRepository.findById(id, pageable);
        } else {
            result = patientRepository.findAll(pageable);
        }
        return result.map(PatientResponseDTO::new);
    }

    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID: " + id + " não encontrado."));

        User user = userRepository.findById(patient.getUser().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID: " + id + " não encontrado."));

        if (!Objects.equals(patientRequestDTO.getCpf(), patient.getCpf())) {
            if (patientRepository.existsByCpf(patientRequestDTO.getCpf()) || userRepository.existsByCpf(patientRequestDTO.getCpf())) {
                throw new ResourceAlreadyExistsException("O CPF já está cadastrado: " + patientRequestDTO.getCpf());
            }
        }

        if (!Objects.equals(patientRequestDTO.getRg(), patient.getRg())) {
            if (patientRepository.existsByRg(patientRequestDTO.getRg())) {
                throw new ResourceAlreadyExistsException("O RG já está cadastrado: " + patientRequestDTO.getRg());
            }
        }

        if (!Objects.equals(patientRequestDTO.getEmail(), patient.getEmail())) {
            if (patientRepository.existsByEmail(patientRequestDTO.getEmail()) || userRepository.existsByEmail(patientRequestDTO.getEmail())) {
                throw new ResourceAlreadyExistsException("O Email já está cadastrado: " + patientRequestDTO.getEmail());
            }
        }

        PatientMapper.updatePatientFromDTO(patientRequestDTO, patient);

        userRepository.save(userMapper.updateUserFromPatientDto(user, patientRequestDTO));

        Patient updatedPatient = patientRepository.save(patient);

        return new PatientResponseDTO(updatedPatient);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletePatient(Long id) {

        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente com ID: " + id + " não encontrado.");
        }

        boolean hasAppointment = appointmentRepository.existsByPatient_Id(id);
        boolean hasExam = examRepository.existsByPatient_Id(id);

        if(hasAppointment || hasExam) {
            throw new IllegalStateException("Cannot delete patient with appointments or exams");
        }

        patientRepository.deleteById(id);
    }

}

