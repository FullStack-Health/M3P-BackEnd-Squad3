package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.PartialUserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    public User toUser(UserRequestDTO dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthdate(dto.getBirthdate());
        user.setCpf(dto.getCpf());
        user.setPassword(dto.getPassword());
        user.setRoleName(dto.getRoleName());
        user.setPhone(dto.getPhone());
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setBirthdate(user.getBirthdate());
        dto.setCpf(user.getCpf());
        dto.setRoleName((user.getRoleName()));
        dto.setPhone((user.getPhone()));
        dto.setPassword(user.getPasswordMasked());
        return dto;
    }

    public void updateUserFromDto(User user, UserRequestDTO dto) {
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthdate(dto.getBirthdate());
        user.setCpf(dto.getCpf());
        user.setPhone(dto.getPhone());
    }

    public User toUser(PartialUserRequestDTO source) {
        User target = new User();
        target.setFullName("-");
        target.setEmail(source.getEmail());
        target.setBirthdate(LocalDate.parse("0001-01-01"));
        target.setCpf(String.valueOf(new Random().nextInt(999999999) + 1));
        target.setRoleName(source.getRole());
        target.setPhone("(00) 0 0000-0000");
        return target;
    }
}
