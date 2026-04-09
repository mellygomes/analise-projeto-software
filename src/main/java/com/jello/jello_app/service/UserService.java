package com.jello.jello_app.service;

import com.jello.jello_app.dto.JwtAuthenticationResponse;
import com.jello.jello_app.dto.LoginRequest;
import com.jello.jello_app.dto.UserDTO;

public interface UserService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);
    UserDTO register(RegisterRequest registerRequest);
}
