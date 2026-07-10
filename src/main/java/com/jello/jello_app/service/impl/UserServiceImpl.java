package com.jello.jello_app.service.impl;

import com.jello.jello_app.domain.RequestContext;
import com.jello.jello_app.dto.*;
import com.jello.jello_app.enumeration.EventType;
import com.jello.jello_app.event.UserEvent;
import com.jello.jello_app.model.Confirmation;
import com.jello.jello_app.model.Role;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.ConfirmationRepository;
import com.jello.jello_app.repository.RoleRepository;
import com.jello.jello_app.repository.UserRepository;
import com.jello.jello_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher publisher;
    private final ConfirmationRepository confirmationRepository;

    @Override
    public User register(RegisterRequest request) {
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found!"));
        try{
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setProfilePicture(null);
            user.setRoles(Set.of(roleUser));
            user.setBio(null);
            user.setEnabled(false);
            User savedUser = userRepository.save(user);
            if(RequestContext.getUserId() == null) {
                user.setCreatedBy(savedUser.getId());
                user.setUpdatedBy(savedUser.getId());
            }

            Confirmation confirmation = new Confirmation((savedUser));
            confirmationRepository.save(confirmation);

            publisher.publishEvent(new UserEvent(savedUser, EventType.REGISTRATION, Map.of("key", confirmation.getConfirmationKey())));

            return savedUser;
        } catch (DataIntegrityViolationException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDTO userDtoBuilder(User user){
        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .bio(user.getBio())
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
                    existingUser.setBio(request.getBio());
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
        String username = authentication.getName();
        return userRepository.findByUsername(username);
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

    @Override
    public User revokeAdmin(Long userId) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(userRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User grantModerator(Long userId) {
        Role moderatorRole = roleRepository.findByName("ROLE_MODERATOR")
                .orElseThrow(() -> new RuntimeException("ROLE_MODERATOR not found!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(moderatorRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public User revokeModerator(Long userId) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(userRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public void verifyAccountKey(String token) {
        Confirmation confirmation = confirmationRepository.findByConfirmationKey(token)
                .orElseThrow(() -> new RuntimeException("Confirmation not found!"));
        User user = userRepository.findByEmail(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        confirmationRepository.delete(confirmation);
    }

    @Override
    public void updateLogin(String username) {
        User user = userRepository.findByUsername(username);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
