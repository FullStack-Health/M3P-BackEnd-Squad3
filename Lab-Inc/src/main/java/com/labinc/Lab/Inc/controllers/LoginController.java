package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.LoginRequestDTO;
import com.labinc.Lab.Inc.dtos.LoginResponseDTO;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.services.UserService;
import com.labinc.Lab.Inc.services.UserToPatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserToPatientService userToPatientService;
    @Autowired(required = false)
    private JwtEncoder jwtEncoder;
    @Autowired(required = false)
    private UserService userService;
    private static final long EXPIRATION_TIME = 36000L;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> generateToken(@Valid @RequestBody LoginRequestDTO loginRequest) {
        User user = userService.validateUser(loginRequest);
        Instant now = Instant.now();

        String scope = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Long patientId = userToPatientService.findPatientIdByUserId(user.getUserId());

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("LabIncBackend")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRATION_TIME))
                .subject(user.getUserId().toString())
                .claim("scope", scope)
                .claim("userFullName", user.getFullName());

        if (patientId != null) {
            claimsBuilder.claim("patientId", patientId);
        }

        JwtClaimsSet claims = claimsBuilder.build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, EXPIRATION_TIME));
    }
}