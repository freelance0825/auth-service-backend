package com.freelance.auth_service.application.service;

import com.freelance.auth_service.application.dto.*;
import com.freelance.auth_service.domain.entity.AuthUserEntity;
import com.freelance.auth_service.infrastructure.port.AuthUserPersistencePort;
import com.freelance.auth_service.shared.config.security.JwtUtil;
import com.freelance.auth_service.shared.exception.EmailNotFoundException;
import com.freelance.auth_service.shared.exception.InvalidRequestException;
import com.freelance.auth_service.shared.exception.UserAlreadyExistException;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final AuthUserPersistencePort authUserPersistencePort;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Description(value = "Register a new user")
    public ResponseEntity<AuthResponseDto> register(RegisterRequestDto request) {

        if (ObjectUtils.isEmpty(request)) {
            throw new InvalidRequestException("Request cannot be empty.");
        }

        if (authUserPersistencePort.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Email already exists");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new InvalidRequestException("Password must be at least 6 characters long");
        }

        AuthUserEntity authUserEntity = new AuthUserEntity();
        authUserEntity.setEmail(request.getEmail());
        authUserEntity.setPassword(passwordEncoder.encode(request.getPassword())); // Hash password before saving

        authUserPersistencePort.save(authUserEntity);

        String token = jwtUtil.generateToken(authUserEntity.getEmail());

        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @Description(value = "User Login")
    public ResponseEntity<AuthResponseDto> login(AuthRequestDto request) {

        AuthUserEntity authUserEntity = authUserPersistencePort.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), authUserEntity.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(authUserEntity.getEmail());

        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @Description(value = "Password Update")
    public ResponseEntity<UpdatePasswordResponseDto> updatePassword(UpdatePasswordRequestDto request) {

        AuthUserEntity authUserEntity = authUserPersistencePort.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("User not found with email: " + request.getEmail()));

        if (!isValidPassword(request.getPassword())) {
            throw new InvalidRequestException("Password must be at least 6 characters long");
        }

        authUserEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        authUserPersistencePort.save(authUserEntity);

        return ResponseEntity.ok().body(new UpdatePasswordResponseDto("Password updated successfully."));

    }

    // Helper method to validate password length (can be extended as needed)
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

}
