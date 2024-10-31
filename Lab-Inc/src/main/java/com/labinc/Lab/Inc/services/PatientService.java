package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.dtos.PatientResponseDTO;
import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.mappers.PatientMapper;
import com.labinc.Lab.Inc.mappers.UserMapper;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import com.labinc.Lab.Inc.services.exceptions.ResourceAlreadyExistsException;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public PatientResponseDTO newPatient(PatientRequestDTO patientRequestDTO) {

        // TODO: Fazer Verificação Código401(Unauthorized)- Falha de autenticação.

        // Verifica se CPF já existe
        if (patientRepository.existsByCpf(patientRequestDTO.getCpf())) {
            throw new ResourceAlreadyExistsException("O CPF já está cadastrado: " + patientRequestDTO.getCpf());
        }

        // Verifica se RG já existe
        if (patientRepository.existsByRg(patientRequestDTO.getRg())) {
            throw new ResourceAlreadyExistsException("O RG já está cadastrado: " + patientRequestDTO.getRg());
        }

        // Verifica se Email já existe
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("O Email já está cadastrado: " + patientRequestDTO.getEmail());
        }

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
        patient = patientRepository.save(patient);

        // Cria e salva o usuário do tipo "PACIENTE" correspondente ao paciente
        User user = new User();
        user.setFullName(patient.getFullName());
        user.setEmail(patient.getEmail());
        user.setBirthdate(patient.getBirthDate());
        user.setCpf(patient.getCpf());
        user.setPassword(passwordEncoder.encode(patient.getCpf()));// Configura a senha como o CPF encriptado
        user.setPasswordMasked(user.getPasswordMasked(patient.getCpf()));
        user.setPhone(patient.getPhone());
        user.setRoleName(AllowedRoles.PACIENTE); // Define o perfil como "PACIENTE"
        userRepository.save(user); // Salva o usuário

        return new PatientResponseDTO(patient);
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO patientById(Long id) {

        //  TODO: Fazer Verificação Código401(Unauthorized)- Falha de autenticação.

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente não encontrado com o id " + id)
        );
        return new PatientResponseDTO(patient);
    }

    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> listPatients(Pageable pageable) {

        //  TODO: Fazer Verificação Código401(Unauthorized)- Falha de autenticação.
        //  TODO: Criar  Parâmetros de query: nome, telefone, e-mail.(Opcional)

        Page<Patient> result = patientRepository.findAll(pageable);
        return result.map(PatientResponseDTO::new);
    }

    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {

        //  TODO: Fazer Verificação Código401(Unauthorized)- Falha de autenticação.

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID: " + id + " não encontrado."));

        PatientMapper.updatePatientFromDTO(patientRequestDTO, patient);

        Patient updatedPatient = patientRepository.save(patient);

        return new PatientResponseDTO(updatedPatient);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletePatient(Long id){

        //  TODO: Fazer Verificação Código401(Unauthorized)- Falha de autenticação.

        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente com ID: " + id + " não encontrado.");
        }
        patientRepository.deleteById(id);
    }

}

