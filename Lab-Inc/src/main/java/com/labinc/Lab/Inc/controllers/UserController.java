package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.UserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.repositories.RoleRepository;
import com.labinc.Lab.Inc.repositories.UserRepository;
import com.labinc.Lab.Inc.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws BadRequestException {
        logger.debug("Creating user with role: {}", userRequestDTO.getRoleName());
        UserResponseDTO savedUser = userService.saveUser(userRequestDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId,  @Valid @RequestBody UserRequestDTO userRequestDTO) throws BadRequestException {
        UserResponseDTO updateUser = userService.updateUser(userId, userRequestDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            Pageable pageable) {

        Page<UserResponseDTO> users = userService.getAllUsers(userId, fullName, email, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("userId") Long userId) {
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userResponseDTO);
    }



}
