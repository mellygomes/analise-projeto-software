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

            User admin = new User();
            admin.setFirstName("Ade");
            admin.setLastName("Miro" + i);
            admin.setEmail(email);
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
            System.out.println("Default admin " + i + " created successfully!");
        }
    }

    private void createDefaultUsers() {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found!"));

        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@email.com";
            String username = "user" + i;
            if (userRepository.existsByEmail(email)) continue;

            User user = new User();
            user.setFirstName("The");
            user.setLastName("User " + i);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user " + i + " created successfully!");
        }
    }
}
