package com.jello.jello_app.auth.service;

import com.jello.jello_app.auth.dto.LoginRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    Authentication login(LoginRequest request);
}
