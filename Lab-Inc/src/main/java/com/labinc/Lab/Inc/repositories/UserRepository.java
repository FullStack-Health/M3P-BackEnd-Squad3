package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByUserId(Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE " +
            "(:userId IS NULL OR u.userId = :userId) AND " +
            "(:email IS NULL OR LOWER(u.email) = LOWER(:email)) AND " +
            "(:fullName IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullName, '%')))")
    Page<User> findByUserIdAndEmailAndFullNameContaining(
            @Param("userId") Long userId,
            @Param("fullName") String fullName,
            @Param("email") String email,
            Pageable pageable);

    Optional<User> findByCpf(String cpf);
}
