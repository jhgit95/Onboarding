package com.sgc.onboarding.auth.dto.signup;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @Schema(description = "사용자 이름", example = "JIN HO")
    private String username;

    @Schema(description = "비밀번호", example = "password1234")
    private String password;

    @Schema(description = "닉네임", example = "Mentos")
    private String nickname;
}
