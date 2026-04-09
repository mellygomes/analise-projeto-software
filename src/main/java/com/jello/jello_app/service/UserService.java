package com.jello.jello_app.service;

import com.jello.jello_app.dto.*;

public interface UserService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);
    ApiResponse register(RegisterRequest registerRequest);
}
