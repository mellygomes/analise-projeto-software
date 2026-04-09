package com.jello.jello_app.security;

import com.jello.jello_app.model.Role;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.RoleRepository;
import com.jello.jello_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoles(defaultRoles);
        createDefaultUsers();
        createDefaultAdmins();
    }

    private void createDefaultRoles(Set<String> roles) {
        roles.forEach(role -> roleRepository.findByName(role)
                .orElseGet(() -> {
                    Role newRole = new Role(role);
                    roleRepository.save(newRole);
                    System.out.println("Created role: " + newRole);
                    return newRole;
                }));
    }

    private void createDefaultAdmins() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found!"));

        for (int i = 1; i <= 2; i++) {
            String email = "admin" + i + "email.com";
            String username = "admin" + i;
            if (userRepository.existsByEmail(email)) continue;

            User admin = new User;
        }
    }
}
