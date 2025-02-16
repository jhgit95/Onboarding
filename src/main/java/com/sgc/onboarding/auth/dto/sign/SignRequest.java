package com.sgc.onboarding.auth.dto.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignRequest {
    @Schema(description = "사용자 이름", example = "JIN HO")
    private String username;

    @Schema(description = "사용자 비밀번호", example = "password1234")
    private String password;
}
