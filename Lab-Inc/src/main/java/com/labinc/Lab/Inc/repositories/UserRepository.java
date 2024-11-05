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

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Page<User> findByUserIdWithPagination(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Page<User> findByEmailWithPagination(@Param("email") String email, Pageable pageable);

    Optional<User> findByCpf(String cpf);

    boolean existsByCpfAndUserIdNot(String cpf, Long userId);

    boolean existsByEmailAndUserIdNot(String email, Long userId);

}
