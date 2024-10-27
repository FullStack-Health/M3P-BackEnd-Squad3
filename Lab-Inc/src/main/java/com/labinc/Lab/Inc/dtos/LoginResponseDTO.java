package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    @Schema(description = "JSON Web Token value")
    private String token;

    @Schema(description = "Time after which the token is no longer valid")
    private Long expirationTime;
}