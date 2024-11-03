package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.controllers.handlers.ConflictException;
import com.labinc.Lab.Inc.dtos.*;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.mappers.UserMapper;
import com.labinc.Lab.Inc.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired(required = false)
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) throws BadRequestException {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException(
                "User not found"));

        if (userRepository.existsByCpf(userRequestDTO.getCpf()) && !user.getCpf().equals(userRequestDTO.getCpf())) {
            throw new DuplicateKeyException("cpf already exists in another user record");
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail()) && !user.getEmail().equals(userRequestDTO.getEmail())) {
            throw new DuplicateKeyException("email already exists in another user record");
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
        if (userId != null) {
            return userRepository.findByUserIdWithPagination(userId, pageable).map(userMapper::toResponseDTO);
        } else if (email != null) {
            return userRepository.findByEmailWithPagination(email, pageable).map(userMapper::toResponseDTO);
        } else {
            return userRepository.findAll(pageable).map(userMapper::toResponseDTO);
        }
    }

    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return userMapper.toResponseDTO(user);
    }

    public User validateUser(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Nome de usuário não encontrado: " + loginRequest.getEmail()));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Senha errada para o usuário " + loginRequest.getEmail());
        }
        return user;
    }

    public void redefinePassword(String email, PasswordRequestDTO passwordRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("E-mail não encontrado: " + email));
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        user.setPasswordMasked(user.getPasswordMasked(passwordRequest.getPassword()));
        userRepository.save(user);
    }

    public UserResponseDTO preRegisterUser(PartialUserRequestDTO userRequest) {

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new DuplicateKeyException("E-mail já cadastrado: " + userRequest.getEmail());
        } else {

            User user = userMapper.toUser(userRequest);

            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setPasswordMasked(user.getPasswordMasked(userRequest.getPassword()));

            return userMapper.toResponseDTO(userRepository.save(user));
        }
    }

}
