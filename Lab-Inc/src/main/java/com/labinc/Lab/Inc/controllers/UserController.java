package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.PasswordRequestDTO;
import com.labinc.Lab.Inc.dtos.PartialUserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired(required = false)
    private UserService service;

    @PutMapping("/email/{email}/redefine-password")
    public ResponseEntity<Void> redefinePassword(@PathVariable String email, @Valid @RequestBody PasswordRequestDTO passwordRequest) {
        service.redefinePassword(email, passwordRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pre-registration")
    public ResponseEntity<UserResponseDTO> preRegisterUser(@Valid @RequestBody PartialUserRequestDTO userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.preRegisterUser(userRequest));
    }
}
