package com.sgc.onboarding.auth.dto.signup;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String username;

    private String password;

    private String nickname;
}
