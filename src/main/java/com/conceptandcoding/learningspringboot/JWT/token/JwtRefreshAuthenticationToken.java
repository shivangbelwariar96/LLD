package com.conceptandcoding.learningspringboot.JWT.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtRefreshAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public JwtRefreshAuthenticationToken(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}