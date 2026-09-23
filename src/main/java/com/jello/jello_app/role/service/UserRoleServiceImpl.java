package com.jello.jello_app.role.service;

import com.jello.jello_app.role.model.Role;
import com.jello.jello_app.role.repository.RoleRepository;
import com.jello.jello_app.user.model.User;
import com.jello.jello_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    @Override
    public User grantAdmin(Long userId) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN não encontrado!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(adminRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @Override
    public User revokeAdmin(Long userId) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER não encontrado!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(userRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @Override
    public User grantModerator(Long userId) {
        Role moderatorRole = roleRepository.findByName("ROLE_MODERATOR")
                .orElseThrow(() -> new RuntimeException("ROLE_MODERATOR não encontrado!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(moderatorRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @Override
    public User revokeModerator(Long userId) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER não encontrado!"));
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setRoles(new HashSet<>(Set.of(userRole)));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }
}
