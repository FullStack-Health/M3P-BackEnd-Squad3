package com.labinc.Lab.Inc.repositories;

import com.labinc.Lab.Inc.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(String roleName);

    Optional<Role> findByRoleName(String roleName);

    Optional<Role> findByRoleId(Long roleId);
}
