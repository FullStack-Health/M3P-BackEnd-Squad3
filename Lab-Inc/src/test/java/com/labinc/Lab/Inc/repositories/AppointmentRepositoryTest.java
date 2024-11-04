package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.Appointment;
import com.labinc.Lab.Inc.entities.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "com.labinc.Lab.Inc.entities")
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    public void setUp() {
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

        testPatient = patientRepository.save(testPatient);


        Appointment appointment1 = new Appointment();
        appointment1.setPatient(testPatient);
        appointment1.setReason("Consultation");
        appointment1.setConsultDate(LocalDate.of(2024, 10, 15));
        appointment1.setConsultTime(LocalTime.of(14, 30));
        appointment1.setProblemDescrip("Persistent headache");
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setPatient(testPatient);
        appointment2.setReason("Follow-up");
        appointment2.setConsultDate(LocalDate.of(2024, 11, 20));
        appointment2.setConsultTime(LocalTime.of(10, 0));
        appointment2.setProblemDescrip("Post-surgery check");
        appointmentRepository.save(appointment2);
    }

    @Test
    public void testFindByPatientId() {
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(testPatient.getId());
        assertThat(appointments).hasSize(2);
    }

    @Test
    public void testExistsByPatientId() {
        boolean exists = appointmentRepository.existsByPatient_Id(testPatient.getId());
        assertThat(exists).isTrue();
    }

    @Test
    public void testFindByPatientIdWithPagination() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Appointment> appointmentsPage = appointmentRepository.findByPatientId(testPatient.getId(), pageable);
        assertThat(appointmentsPage.getContent()).hasSize(1);
        assertThat(appointmentsPage.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void testFindByPatientIdAndReasonContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Appointment> resultPage = appointmentRepository.findByPatientIdAndReasonContaining(
                testPatient.getId(), "consult", pageable);

        assertThat(resultPage.getContent()).hasSize(1);
        assertThat(resultPage.getContent().getFirst().getReason()).isEqualTo("Consultation");
    }
}
