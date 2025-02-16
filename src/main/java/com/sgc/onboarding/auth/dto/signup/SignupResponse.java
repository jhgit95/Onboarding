package com.sgc.onboarding.auth.dto.signup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class SignupResponse {
    @Schema(description = "사용자 이름", example = "JIN HO")
    private String username;

    @Schema(description = "사용자 닉네임", example = "Mentos")
    private String nickname;

    @NotNull
    @Schema(description = "권한 이름", example = "ROLE_USER")
    private final List<SimpleGrantedAuthority> authorities;
}