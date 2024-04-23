package com.example.bpmsenterprise.components.authentication.configs;

import com.example.bpmsenterprise.components.authentication.token.Token;
import com.example.bpmsenterprise.components.authentication.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.mockito.Mockito.*;

class LogoutServiceTest {
    @Mock
    private TokenRepository tokenRepository;
    private LogoutHandler logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        logoutService = new LogoutService(tokenRepository);
    }

    @Test
    void testLogoutWithValidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-jwt-token");
        when(tokenRepository.findByToken("valid-jwt-token")).thenReturn(Optional.of(new Token()));
        logoutService.logout(request, response, authentication);
        verify(tokenRepository).findByToken("valid-jwt-token");
        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    void testLogoutWithInvalidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");
        logoutService.logout(request, response, authentication);
        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
    }
}