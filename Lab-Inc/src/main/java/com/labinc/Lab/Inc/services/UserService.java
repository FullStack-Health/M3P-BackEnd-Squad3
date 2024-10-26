package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.controllers.handlers.ConflictException;
import com.labinc.Lab.Inc.dtos.UserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.mappers.UserMapper;
import com.labinc.Lab.Inc.repositories.RoleRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) throws BadRequestException {


        if (userRequestDTO.getFullName() == null || userRequestDTO.getFullName().isEmpty()) {
            throw new BadRequestException("name is mandatory");
        }
        if (userRequestDTO.getEmail() == null || userRequestDTO.getEmail().isEmpty()) {
            throw new BadRequestException("email is mandatory");
        }
        if (userRequestDTO.getBirthdate() == null) {
            throw new BadRequestException("birthdate is mandatory");
        }
        if (userRequestDTO.getCpf() == null || userRequestDTO.getCpf().isEmpty()) {
            throw new BadRequestException("cpf is mandatory");
        }
        if (userRequestDTO.getPassword() == null || userRequestDTO.getPassword().isEmpty()) {
            throw new BadRequestException("password is mandatory");
        }
        if (userRequestDTO.getPhone() == null || userRequestDTO.getPhone().isEmpty()) {
            throw new BadRequestException("phone is mandatory");
        }

        if (userRepository.existsByCpf(userRequestDTO.getCpf())) {
            throw new ConflictException("cpf already exists");
        }
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ConflictException("email already exists");
        }

        User user = userMapper.toUser(userRequestDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) throws BadRequestException {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException(
                "User not found"));

        if (userRequestDTO.getFullName() == null || userRequestDTO.getFullName().isEmpty()) {
            throw new BadRequestException("name is mandatory");
        }
        if (userRequestDTO.getEmail() == null || userRequestDTO.getEmail().isEmpty()) {
            throw new BadRequestException("email is mandatory");
        }
        if (userRequestDTO.getBirthdate() == null) {
            throw new BadRequestException("birthdate is mandatory");
        }
        if (userRequestDTO.getCpf() == null || userRequestDTO.getCpf().isEmpty()) {
            throw new BadRequestException("cpf is mandatory");
        }
        if (userRequestDTO.getPassword() == null || userRequestDTO.getPassword().isEmpty()) {
            throw new BadRequestException("password is mandatory");
        }
        if (userRequestDTO.getPhone() == null || userRequestDTO.getPhone().isEmpty()) {
            throw new BadRequestException("phone is mandatory");
        }

        if (userRepository.existsByCpf(userRequestDTO.getCpf()) && !user.getCpf().equals(userRequestDTO.getCpf())) {
            throw new ConflictException("cpf already exists in another user record");
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail()) && !user.getEmail().equals(userRequestDTO.getEmail())) {
            throw new ConflictException("email already exists in another user record");
        }

        userMapper.updateUserFromDto(user, userRequestDTO);

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }


    public void deleteUser(Long userId) {
        if (!userRepository.existsByUserId(userId)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public Page<UserResponseDTO> getAllUsers(Long userId, String fullName, String email, Pageable pageable) {

        if (userId == null && fullName == null && email == null) {
            return userRepository.findAll(pageable).map(userMapper::toResponseDTO);
        }

        Page<User> users = userRepository.findByUserIdAndEmailAndFullNameContaining(userId, fullName, email, pageable);
        return users.map(userMapper::toResponseDTO);
    }

    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return userMapper.toResponseDTO(user);
    }




}

