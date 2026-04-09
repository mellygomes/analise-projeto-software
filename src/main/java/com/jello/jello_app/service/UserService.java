package com.jello.jello_app.service;

import com.jello.jello_app.dto.JwtAuthenticationResponse;
import com.jello.jello_app.dto.LoginRequest;

public interface UserService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);
}
