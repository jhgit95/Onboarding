package com.sgc.onboarding.common.config;

import com.sgc.onboarding.common.dto.UserRole;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secretKey = "my-secret-key-for-jwt-signing-my-secret-key-for-jwt-signing"; // 테스트용 키

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        ReflectionTestUtils.setField(jwtUtil, "secretKey", encodedKey);
        jwtUtil.init();
    }

    @Test
    void createAccessToken_ShouldReturnValidToken() {
        String token = jwtUtil.createAccessToken(1L, "testUser", UserRole.USER);
        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
    }

    @Test
    void createRefreshToken_ShouldReturnValidToken() {
        String token = jwtUtil.createRefreshToken(1L);
        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
    }

    @Test
    void substringToken_ShouldExtractTokenWithoutBearer() {
        String token = "Bearer myToken123";
        assertEquals("myToken123", jwtUtil.substringToken(token));
    }

    @Test
    void substringToken_ShouldThrowException_WhenInvalidFormat() {
        String token = "InvalidToken";
        assertThrows(NoSuchElementException.class, () -> jwtUtil.substringToken(token));
    }

    @Test
    void extractClaims_ShouldReturnCorrectClaims() {
        String token = jwtUtil.createAccessToken(1L, "testUser", UserRole.USER);
        String extractedToken = jwtUtil.substringToken(token);

        Claims claims = jwtUtil.extractClaims(extractedToken);
        assertEquals("1", claims.getSubject());
        assertEquals("testUser", claims.get("nickname"));
        assertEquals("USER", claims.get("userRole"));
    }

    @Test
    void isTokenExpired_ShouldReturnFalse_ForValidToken() {
        String token = jwtUtil.createAccessToken(1L, "testUser", UserRole.USER);
        String extractedToken = jwtUtil.substringToken(token);

        assertFalse(jwtUtil.isTokenExpired(extractedToken));
    }


}
