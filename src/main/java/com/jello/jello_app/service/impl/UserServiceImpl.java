package com.jello.jello_app.service.impl;

import com.jello.jello_app.dto.JwtAuthenticationResponse;
import com.jello.jello_app.dto.LoginRequest;
import com.jello.jello_app.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        return null;
    }
}
