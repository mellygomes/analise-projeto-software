package com.jello.jello_app.service.impl;

import com.jello.jello_app.dto.*;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.UserRepository;
import com.jello.jello_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse register(RegisterRequest registerRequest) {
        User user = new User();
        ApiResponse response = new ApiResponse();
        try{
            if(userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new RuntimeException(registerRequest.getEmail() + " already exists");
            }
            if(userRepository.existsByUsername(registerRequest.getUsername())){
                throw new RuntimeException(registerRequest.getUsername() + " already exists");
            }
            user.setEmail(registerRequest.getEmail());
            user.setUsername(registerRequest.getUsername());
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            User savedUser = userRepository.save(user);
            response.setData(savedUser);
            response.setMessage("User registered successfully");
        } catch (Exception e){
            response.setMessage(e.getMessage());
            response.setData(null);
        }
        return response;
    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        return null;
    }

    private UserDTO userDtoBuilder(User user){
        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
