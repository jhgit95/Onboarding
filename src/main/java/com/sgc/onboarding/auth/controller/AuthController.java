package com.sgc.onboarding.auth.controller;

import com.sgc.onboarding.auth.dto.sign.SignRequest;
import com.sgc.onboarding.auth.dto.sign.SignResponse;
import com.sgc.onboarding.auth.dto.signup.SignupRequest;
import com.sgc.onboarding.auth.dto.signup.SignupResponse;
import com.sgc.onboarding.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/v1/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest reqDto) {
        return ResponseEntity.ok(authService.signup(reqDto));
    }

    @PostMapping("/v1/sign")
    public ResponseEntity<SignResponse> login(@RequestBody SignRequest reqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.sign(reqDto));
    }
}
