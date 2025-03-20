package com.freelance.auth_service.api.controller;

import com.freelance.auth_service.application.dto.*;
import com.freelance.auth_service.application.service.AuthUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthUserController {


    private final AuthUserService authUserService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return authUserService.register(request);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdatePasswordResponseDto> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto request) {
        return authUserService.updatePassword(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto request) {
        return authUserService.login(request);
    }

}
