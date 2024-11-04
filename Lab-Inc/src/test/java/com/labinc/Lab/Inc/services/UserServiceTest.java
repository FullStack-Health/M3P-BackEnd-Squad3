package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.controllers.handlers.ConflictException;
import com.labinc.Lab.Inc.dtos.*;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.mappers.UserMapper;
import com.labinc.Lab.Inc.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setEmail("teste@exemplo.com");
        user.setPassword("password");
        user.setCpf("12345678900");

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("teste@exemplo.com");
        userRequestDTO.setCpf("12345678900");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(user.getUserId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCpf(user.getCpf());
        userResponseDTO.setPassword(user.getPasswordMasked());
    }

    @Test
    void testUpdateUser_Success() throws BadRequestException {
        userRequestDTO.setEmail("novo@exemplo.com");
        userResponseDTO.setEmail(userRequestDTO.getEmail());
        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);
        doNothing().when(userMapper).updateUserFromDto(any(User.class), any(UserRequestDTO.class));

        UserResponseDTO response = userService.updateUser(1L, userRequestDTO);

        assertEquals("novo@exemplo.com", response.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUser_Conflict_CpfExists() {
        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsByCpf(anyString())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () ->
                userService.updateUser(1L, userRequestDTO)
        );

        assertEquals("cpf already exists in another user record", exception.getMessage());
    }

    @Test
    void testUpdateUser_Conflict_EmailExists() {
        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () ->
                userService.updateUser(1L, userRequestDTO)
        );

        assertEquals("email already exists in another user record", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsByUserId(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsByUserId(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                userService.deleteUser(1L)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetAllUsers_Success() {
        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(user)));
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        Page<UserResponseDTO> response = userService.getAllUsers(null, null, Pageable.unpaged());

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals("teste@exemplo.com", response.getContent().getFirst().getEmail());
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        UserResponseDTO response = userService.getUserById(1L);

        assertEquals("teste@exemplo.com", response.getEmail());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                userService.getUserById(1L)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testValidateUser_Success() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("teste@exemplo.com", "password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User validatedUser = userService.validateUser(loginRequest);

        assertEquals("teste@exemplo.com", validatedUser.getEmail());
    }

    @Test
    void testValidateUser_NotFound() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("teste@exemplo.com", "password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.validateUser(loginRequest)
        );

        assertEquals("Nome de usuário não encontrado: teste@exemplo.com", exception.getMessage());
    }

    @Test
    void testRedefinePassword_Success() {
        PasswordRequestDTO passwordRequest = new PasswordRequestDTO("newPassword");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.redefinePassword("teste@exemplo.com", passwordRequest);

        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testRedefinePassword_NotFound() {
        PasswordRequestDTO passwordRequest = new PasswordRequestDTO("newPassword");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.redefinePassword("teste@exemplo.com", passwordRequest)
        );

        assertEquals("E-mail não encontrado: teste@exemplo.com", exception.getMessage());
    }

    @Test
    void testPreRegisterUser_Success() {
        PartialUserRequestDTO partialUserRequest = new PartialUserRequestDTO();
        partialUserRequest.setEmail("novouser@exemplo.com");
        partialUserRequest.setPassword("password");
        userResponseDTO.setEmail(partialUserRequest.getEmail());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toUser(any(PartialUserRequestDTO.class))).thenReturn(user);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn((userResponseDTO));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserResponseDTO response = userService.preRegisterUser(partialUserRequest);

        assertEquals("novouser@exemplo.com", response.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void testPreRegisterUser_DuplicateEmail() {
        PartialUserRequestDTO partialUserRequest = new PartialUserRequestDTO();
        partialUserRequest.setEmail("duplicata@exemplo.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () ->
                userService.preRegisterUser(partialUserRequest)
        );

        assertEquals("E-mail já cadastrado: duplicata@exemplo.com", exception.getMessage());
    }
}
