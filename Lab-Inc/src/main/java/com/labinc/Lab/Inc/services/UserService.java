package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.controllers.handlers.ConflictException;
import com.labinc.Lab.Inc.dtos.*;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.mappers.UserMapper;
import com.labinc.Lab.Inc.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;
    @Autowired(required = false)
    private BCryptPasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setPasswordMasked(user.getPasswordMasked(userRequestDTO.getPassword()));
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
            if (userRepository.findByCpf(user.getCpf()).isPresent()) {
                throw new DuplicateKeyException("CPF já cadastrado: " + user.getCpf());
            }
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setPasswordMasked(user.getPasswordMasked(userRequest.getPassword()));
            return userMapper.toResponseDTO(userRepository.save(user));
        }
    }
}
