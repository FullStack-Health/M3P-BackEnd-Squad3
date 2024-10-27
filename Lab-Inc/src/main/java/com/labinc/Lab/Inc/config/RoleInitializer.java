package com.labinc.Lab.Inc.config;

import com.labinc.Lab.Inc.entities.Role;
import com.labinc.Lab.Inc.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_MEDICO", "ROLE_PACIENTE");

        for (String roleName : roles) {
            if (!roleRepository.existsByRoleName(roleName)) {
                Role role = new Role();
                role.setRoleName(roleName);
                roleRepository.save(role);
            }
        }
    }
}