package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.dtos.PatientResponseDTO;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.repositories.PatientRepository;
import com.labinc.Lab.Inc.services.exceptions.ResourceAlreadyExistsException;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
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

        // Converter PatientRequestDTO para entidade Patient
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

        // Criar o DTO de resposta
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO(patient);
        patientResponseDTO.setId(patient.getId());
        patientResponseDTO.setFullName(patient.getFullName());
        patientResponseDTO.setGender(patient.getGender());
        patientResponseDTO.setBirthDate(patient.getBirthDate());
        patientResponseDTO.setCpf(patient.getCpf());
        patientResponseDTO.setRg(patient.getRg());
        patientResponseDTO.setMaritalStatus(patient.getMaritalStatus());
        patientResponseDTO.setPhone(patient.getPhone());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setPlaceOfBirth(patient.getPlaceOfBirth());
        patientResponseDTO.setEmergencyContact(patient.getEmergencyContact());
        patientResponseDTO.setListOfAllergies(patient.getListOfAllergies());
        patientResponseDTO.setListCare(patient.getListCare());
        patientResponseDTO.setHealthInsurance(patient.getHealthInsurance());
        patientResponseDTO.setHealthInsuranceNumber(patient.getHealthInsuranceNumber());
        patientResponseDTO.setHealthInsuranceVal(patient.getHealthInsuranceVal());
        patientResponseDTO.setZipcode(patient.getZipcode());
        patientResponseDTO.setStreet(patient.getStreet());
        patientResponseDTO.setAddressNumber(patient.getAddressNumber());
        patientResponseDTO.setNeighborhood(patient.getNeighborhood());
        patientResponseDTO.setCity(patient.getCity());
        patientResponseDTO.setState(patient.getState());
        patientResponseDTO.setComplement(patient.getComplement());
        patientResponseDTO.setReferencePoint(patient.getReferencePoint());

        return patientResponseDTO;
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO patientById(Long id) {

//      TODO: Fazer Verificação Código401(Unauthorized)- Falha de autenticação.

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente não encontrado com o id " + id)
        );
        return new PatientResponseDTO(patient);
    }


}

