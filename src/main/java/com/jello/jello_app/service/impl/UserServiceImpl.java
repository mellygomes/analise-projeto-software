package com.jello.jello_app.service.impl;

import com.jello.jello_app.dto.*;
import com.jello.jello_app.model.Role;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.RoleRepository;
import com.jello.jello_app.repository.UserRepository;
import com.jello.jello_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User register(RegisterRequest request) {
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found!"));
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .filter(user -> !userRepository.existsByUsername(request.getUsername()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setUsername(request.getUsername());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setProfilePicture(null);
                    user.setRoles(Set.of(roleUser));
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User or email already registered!"));
    }

    @Override
    public UserDTO userDtoBuilder(User user){
        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    @Override
    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository :: delete, () -> {
                    throw new RuntimeException("User not found");
                });
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public User grantAdmin(Long userId) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(adminRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
