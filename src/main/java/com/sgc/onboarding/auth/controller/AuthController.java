package com.sgc.onboarding.auth.controller;

import com.sgc.onboarding.auth.dto.sign.SignRequest;
import com.sgc.onboarding.auth.dto.sign.SignResponse;
import com.sgc.onboarding.auth.dto.signup.SignupRequest;
import com.sgc.onboarding.auth.dto.signup.SignupResponse;
import com.sgc.onboarding.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth API", description = "인증 및 회원가입 관련 API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/v1/signup")
    @Operation(summary = "회원가입 API", description = "회원가입 기능입니다.")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest reqDto) {
        return ResponseEntity.ok(authService.signup(reqDto));
    }

    @PostMapping("/v1/sign")
    @Operation(
            summary = "로그인 API",
            description = "사용자 이름과 비밀번호로 로그인을 처리하고 JWT 토큰을 반환합니다."
    )
    public ResponseEntity<SignResponse> login(@RequestBody SignRequest reqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.sign(reqDto));
    }
}
