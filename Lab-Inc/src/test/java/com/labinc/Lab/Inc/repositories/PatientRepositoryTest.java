package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.entities.Patient;
import com.labinc.Lab.Inc.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "com.labinc.Lab.Inc.entities")
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    private Patient testPatient;
    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setRoleName(AllowedRoles.SCOPE_PACIENTE);
        testUser.setFullName("Cristian User");
        testUser.setEmail("user@exemplo.com");
        testUser.setBirthdate(LocalDate.of(1990, 5, 21));
        testUser.setCpf("000.888.456-12");
        testUser.setPhone("(48) 9 9856-2345");
        testUser.setPassword("securePassword");
        testUser = userRepository.save(testUser);

        testPatient = new Patient();
        testPatient.setFullName("Cristian Yamamoto");
        testPatient.setGender("Masculino");
        testPatient.setBirthDate(LocalDate.of(1990, 5, 21));
        testPatient.setCpf("123.456.789-00");
        testPatient.setRg("2356987");
        testPatient.setMaritalStatus("Solteiro");
        testPatient.setPhone("(48) 9 9999-9999");
        testPatient.setEmail("cristian@exemplo.com");
        testPatient.setPlaceOfBirth("Florianópolis");
        testPatient.setEmergencyContact("(48) 9 9999-1234");
        testPatient.setZipcode("88010-000");
        testPatient.setUser(testUser);

        testPatient = patientRepository.save(testPatient);
    }

    @Test
    public void testExistsByCpf() {
        boolean exists = patientRepository.existsByCpf(testPatient.getCpf());
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByRg() {
        boolean exists = patientRepository.existsByRg(testPatient.getRg());
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByEmail() {
        boolean exists = patientRepository.existsByEmail(testPatient.getEmail());
        assertThat(exists).isTrue();
    }

    @Test
    public void testFindByFullNameContainingIgnoreCase() {
        Page<Patient> patientsPage = patientRepository.findByFullNameContainingIgnoreCase("Cristian", PageRequest.of(0, 10));
        assertThat(patientsPage.getContent()).hasSize(1);
        assertThat(patientsPage.getContent().getFirst().getFullName()).isEqualTo("Cristian Yamamoto");
    }

    @Test
    public void testFindByPhoneContaining() {
        Page<Patient> patientsPage = patientRepository.findByPhoneContaining("9999", PageRequest.of(0, 10));
        assertThat(patientsPage.getContent()).hasSize(1);
        assertThat(patientsPage.getContent().getFirst().getPhone()).isEqualTo("(48) 9 9999-9999");
    }

    @Test
    public void testFindByEmailIgnoreCase() {
        Page<Patient> patientsPage = patientRepository.findByEmailIgnoreCase("cristian@exemplo.com", PageRequest.of(0, 10));
        assertThat(patientsPage.getContent()).hasSize(1);
        assertThat(patientsPage.getContent().getFirst().getEmail()).isEqualTo("cristian@exemplo.com");
    }

    @Test
    public void testFindByUser_UserId() {
        Patient foundPatient = patientRepository.findByUser_UserId(testUser.getUserId());
        assertThat(foundPatient).isNotNull();
        assertThat(foundPatient.getUser().getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    public void testFindById() {
        Page<Patient> patientsPage = patientRepository.findById(testPatient.getId(), PageRequest.of(0, 10));
        assertThat(patientsPage.getContent()).hasSize(1);
        assertThat(patientsPage.getContent().getFirst().getId()).isEqualTo(testPatient.getId());
    }
}
