package com.labinc.Lab.Inc.mappers;

import com.labinc.Lab.Inc.dtos.PartialUserRequestDTO;
import com.labinc.Lab.Inc.dtos.PatientRequestDTO;
import com.labinc.Lab.Inc.dtos.UserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setCpf(user.getCpf());
        dto.setRoleName((user.getRoleName()));
        dto.setPhone((user.getPhone()));
        dto.setPassword(user.getPasswordMasked());
        return dto;
    }

    public void updateUserFromDto(User user, UserRequestDTO dto) {
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setCpf(dto.getCpf());
        user.setPhone(dto.getPhone());
    }

    public User updateUserFromPatientDto(User user, PatientRequestDTO dto) {
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setCpf(dto.getCpf());
        user.setPhone(dto.getPhone());

        return user;
    }

    public User toUser(PartialUserRequestDTO source) {
        User target = new User();
        target.setFullName(source.getFullName());
        target.setEmail(source.getEmail());
        target.setRoleName(source.getRoleName());
        target.setPassword(source.getPassword());
        return target;
    }

}
