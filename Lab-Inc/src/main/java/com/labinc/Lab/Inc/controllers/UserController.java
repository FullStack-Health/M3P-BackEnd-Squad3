package com.labinc.Lab.Inc.controllers;

import com.labinc.Lab.Inc.dtos.PartialUserRequestDTO;
import com.labinc.Lab.Inc.dtos.PasswordRequestDTO;
import com.labinc.Lab.Inc.dtos.UserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Atualiza um usuário existente", description = "Atualiza os dados de um usuário existente com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UserResponseDTO> updateUser(@Parameter(description = "ID do usuário", required = true, example = "1") @PathVariable Long userId, @Valid @RequestBody UserRequestDTO userRequestDTO) throws BadRequestException {
        UserResponseDTO updateUser = userService.updateUser(userId, userRequestDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Deleta um usuário existente", description = "Deleta um usuário existente com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID do usuário", required = true, example = "1") @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Obtém todos os usuários", description = "Obtém uma lista paginada de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários recuperados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @Parameter(description = "ID do usuário", example = "1") @RequestParam(required = false) Long userId,
            @Parameter(description = "Nome completo do usuário", example = "João da Silva") @RequestParam(required = false) String fullName,
            @Parameter(description = "Email do usuário", example = "joao.silva@example.com") @RequestParam(required = false) String email,
            Pageable pageable) {

        Page<UserResponseDTO> users = userService.getAllUsers(userId, email, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Obtém um usuário por ID", description = "Obtém os dados de um usuário com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UserResponseDTO> getUserById( @Parameter(description = "ID do usuário", required = true, example = "1") @PathVariable("userId") Long userId) {
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/email/{email}/redefine-password")
    @Operation(summary = "Redefine a senha do usuário", description = "Redefine a senha do usuário com base no email fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha redefinida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> redefinePassword(@Parameter(description = "Email do usuário", required = true, example = "joao.silva@example.com") @PathVariable String email, @Valid @RequestBody PasswordRequestDTO passwordRequest) {
        userService.redefinePassword(email, passwordRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pre-registration")
    @Operation(summary = "Pré-registra um usuário", description = "Pré-registra um usuário com dados parciais fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário pré-registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<UserResponseDTO> preRegisterUser(@Valid @RequestBody PartialUserRequestDTO userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.preRegisterUser(userRequest));
    }
}
