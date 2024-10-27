package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.PartialUserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class UserMapper {
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

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setBirthdate(user.getBirthdate());
        dto.setCpf(user.getCpf());
        dto.setRoleName(user.getRoleName());
        dto.setPhone(user.getPhone());
        dto.setPassword(user.getPasswordMasked());
        return dto;
    }
}
