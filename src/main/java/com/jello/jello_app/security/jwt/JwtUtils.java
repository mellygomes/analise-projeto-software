package com.jello.jello_app.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private Integer expirationTime;

    public String generateTokenForUser(Authentication authenticaition) {
        AppUserDetails userPrincipal = (AppUserDetails) authenticaition.getPrincipal();

        List<String> roles = userPrincipal;
    }
}
