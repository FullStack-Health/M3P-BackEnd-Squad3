package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "com.labinc.Lab.Inc.entities")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setRoleName(AllowedRoles.SCOPE_PACIENTE);
        testUser.setFullName("Cristian User");
        testUser.setEmail("user@exemplo.com");
        testUser.setBirthdate(LocalDate.of(1990, 5, 21));
        testUser.setCpf("000.000.000-99");
        testUser.setPhone("(48) 9 9856-2345");
        testUser.setPassword("securePassword");
        testUser = userRepository.save(testUser);
    }

    @Test
    public void testExistsByCpf() {
        boolean exists = userRepository.existsByCpf(testUser.getCpf());
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByEmail() {
        boolean exists = userRepository.existsByEmail(testUser.getEmail());
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByUserId() {
        boolean exists = userRepository.existsByUserId(testUser.getUserId());
        assertThat(exists).isTrue();
    }

    @Test
    public void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail(testUser.getEmail());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testFindByUserId() {
        Optional<User> foundUser = userRepository.findByUserId(testUser.getUserId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    public void testFindByUserIdWithPagination() {
        Page<User> userPage = userRepository.findByUserIdWithPagination(testUser.getUserId(), PageRequest.of(0, 10));
        assertThat(userPage.getContent()).hasSize(1);
        assertThat(userPage.getContent().getFirst().getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    public void testFindByEmailWithPagination() {
        Page<User> userPage = userRepository.findByEmailWithPagination(testUser.getEmail(), PageRequest.of(0, 10));
        assertThat(userPage.getContent()).hasSize(1);
        assertThat(userPage.getContent().getFirst().getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testFindByCpf() {
        Optional<User> foundUser = userRepository.findByCpf(testUser.getCpf());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getCpf()).isEqualTo(testUser.getCpf());
    }
}
