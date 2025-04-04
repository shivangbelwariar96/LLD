package com.conceptandcoding.learningspringboot.JWT.filters;

import com.conceptandcoding.learningspringboot.JWT.dto.LoginRequest;
import com.conceptandcoding.learningspringboot.JWT.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/login"); // Override default login URL
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Invalid login request");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        String token = jwtUtil.generateToken(authResult.getName(), 15); //15min
        String refreshToken = jwtUtil.generateToken(authResult.getName(), 7 * 24 * 60); //7day

        response.setHeader("Authorization", "Bearer " + token);

        // Set Refresh Token in HttpOnly Cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true); // Set to true if using HTTPS
        refreshCookie.setPath("/refresh-token"); // Cookie available only for refresh endpoint
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days expiry
        response.addCookie(refreshCookie);
    }
}
