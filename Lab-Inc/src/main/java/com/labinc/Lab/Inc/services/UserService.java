package com.labinc.Lab.Inc.services;

import com.labinc.Lab.Inc.dtos.LoginRequestDTO;
import com.labinc.Lab.Inc.dtos.PasswordRequestDTO;
import com.labinc.Lab.Inc.dtos.PartialUserRequestDTO;
import com.labinc.Lab.Inc.dtos.UserResponseDTO;
import com.labinc.Lab.Inc.entities.User;
import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.mappers.UserMapper;
import com.labinc.Lab.Inc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired(required = false)
    private UserRepository repository;
    @Autowired(required = false)
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired(required = false)
    private UserMapper userMapper;

    public User validateUser(LoginRequestDTO loginRequest) {
        User user = repository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Nome de usuário não encontrado: " + loginRequest.getEmail()));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Senha errada para o usuário " + loginRequest.getEmail());
        }
        return user;
    }

    public void redefinePassword(String email, PasswordRequestDTO passwordRequest) {
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("E-mail não encontrado: " + email));
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        user.setPasswordMasked(getPasswordMasked(passwordRequest.getPassword()));
        repository.save(user);
    }

    public UserResponseDTO preRegisterUser(PartialUserRequestDTO userRequest) {
        if (repository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new DuplicateKeyException("E-mail já cadastrado: " + userRequest.getEmail());
        } else if (userRequest.getRole() == AllowedRoles.PATIENT) {
            throw new EnumConstantNotPresentException(AllowedRoles.class, "PATIENT");
        } else {
            User user = userMapper.toUser(userRequest);
            if (repository.findByCpf(user.getCpf()).isPresent()) {
                throw new DuplicateKeyException("CPF já cadastrado: " + user.getCpf());
            }
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setPasswordMasked(getPasswordMasked(userRequest.getPassword()));
            return userMapper.toResponseDTO(repository.save(user));
        }
    }

    private String getPasswordMasked(String password) {
        int passwordLength = password.length();
        if (passwordLength > 4) {
            String[] passwordCharacters = password.split("");
            for (int i = 4; i < passwordLength; i++) {
                passwordCharacters[i] = "*";
            }
            StringBuilder visiblePassword = new StringBuilder();
            for (String character : passwordCharacters) {
                visiblePassword.append(character);
            }
            return visiblePassword.toString();
        } else {
           return password;
        }
    }
}
