package com.sgc.onboarding.auth.service;

import com.sgc.onboarding.auth.dto.sign.SignRequest;
import com.sgc.onboarding.auth.dto.sign.SignResponse;
import com.sgc.onboarding.auth.dto.signup.SignupRequest;
import com.sgc.onboarding.auth.dto.signup.SignupResponse;
import com.sgc.onboarding.security.jwt.JwtUtil;
import com.sgc.onboarding.common.exception.BadRequestException;
import com.sgc.onboarding.user.entity.User;
import com.sgc.onboarding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoders;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ValueOperations<String, String> valueOperations;

    private static final long REFRESH_TOKEN_EXPIRATION_SECONDS = 60 * 60 * 24 * 7; // 7일 (초 단위)

    @Transactional
    public SignupResponse signup(SignupRequest reqDto) {
        if (userService.getUserFromUsername(reqDto.getUsername()) != null) {
            throw new BadRequestException("이미 존재하는 이름입니다.");
        }

        if (userService.getUserFromNickname(reqDto.getNickname()) != null) {
            throw new BadRequestException("이미 존재하는 별명입니다.");
        }

        String encodedPassword = passwordEncoders.encode(reqDto.getPassword());

        User user = new User(reqDto.getUsername(), encodedPassword, reqDto.getNickname());
        User createUser = userService.createUser(user);

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(user.getUserRole().getUserRole())
        );

        return new SignupResponse(createUser.getUsername(), createUser.getNickname(), authorities);
    }

    @Transactional
    public SignResponse sign(SignRequest reqDto) {
        User user = userService.getUserFromUsername(reqDto.getUsername());

        if (!passwordEncoders.matches(reqDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("존재하지 않는 계정 또는 틀린 비밀번호입니다.");
        }

        // 액세스 토큰 생성
        String accessToken = jwtUtil.createAccessToken(
                user.getId(),
                user.getNickname(),
                user.getUserRole()
        );

        // 리프래시 토큰 생성 (서버에서만 관리)
        String refreshToken = jwtUtil.createRefreshToken(user.getId());

        // Redis에 저장 (userId를 key로, refreshToken을 value로 저장)
        valueOperations.set("refresh:" + user.getId(), refreshToken, REFRESH_TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);

        // 클라이언트에게는 액세스 토큰만 반환
        return new SignResponse(accessToken);
    }
}
