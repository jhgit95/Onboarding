package com.sgc.onboarding.auth.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private String username;

    private String nickname;

    private List<Authority> authorities;

    @Getter
    @AllArgsConstructor
    public static class Authority {
        private String authorityName;
    }
}