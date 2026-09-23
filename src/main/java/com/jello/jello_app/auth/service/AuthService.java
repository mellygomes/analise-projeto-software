package com.jello.jello_app.auth.service;

import com.jello.jello_app.auth.dto.LoginRequest;
import com.jello.jello_app.user.model.User;
import org.springframework.security.core.Authentication;

public interface AuthService {
    Authentication login(LoginRequest request);

    User getAuthenticatedUser();

    void verifyAccountKey(String token);

    void updateLogin(String username);
}
