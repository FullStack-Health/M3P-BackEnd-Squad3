package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.Exam;
import com.labinc.Lab.Inc.entities.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "com.labinc.Lab.Inc.entities")
public class ExamRepositoryTest {

    @Autowired
    private ExamRepository examRepository;

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

        Exam exam1 = new Exam();
        exam1.setPatient(testPatient);
        exam1.setExamName("Exame de Sangue");
        exam1.setExamDate(LocalDate.of(2024, 10, 15));
        exam1.setExamTime(LocalTime.of(14, 30));
        exam1.setExamType("Laboratorial");
        exam1.setLab("Laboratório XYZ");
        examRepository.save(exam1);

        Exam exam2 = new Exam();
        exam2.setPatient(testPatient);
        exam2.setExamName("Raio-X");
        exam2.setExamDate(LocalDate.of(2024, 11, 20));
        exam2.setExamTime(LocalTime.of(10, 0));
        exam2.setExamType("Radiológico");
        exam2.setLab("Laboratório XYZ");
        examRepository.save(exam2);
    }

    @Test
    public void testFindByPatientId() {
        List<Exam> exams = examRepository.findByPatient_Id(testPatient.getId());
        assertThat(exams).hasSize(2);
    }
}
