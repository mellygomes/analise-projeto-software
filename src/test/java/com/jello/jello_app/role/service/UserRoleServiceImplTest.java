package com.jello.jello_app.role.service;

import com.jello.jello_app.role.model.Role;
import com.jello.jello_app.role.repository.RoleRepository;
import com.jello.jello_app.user.model.User;
import com.jello.jello_app.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    // Testa a permissão de admin dada ao usuario
    @Test
    void shouldGrantAdminRole() {
        Role role = createRole("ROLE_ADMIN");

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(createRole("ROLE_USER"))));

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User savedUser = userRoleService.grantAdmin(1L);
        boolean hasAdminRole = savedUser.getRoles()
                .stream()
                .anyMatch(roleUser -> roleUser.getName().equals("ROLE_ADMIN"));

        assertTrue(hasAdminRole, "O usuario deveria possuir a ROLE_ADMIN");
        assertEquals(2, savedUser.getRoles().size());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
    }

    // Testa o caminho onde tenta dar a permissao quando o usuario nao existe
    @Test
    void shouldNotGrantAdminWhenUserNotFound() {
        Role adminRole = createRole("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRoleService.grantAdmin(1L));

        assertEquals("Usuário não encontrado!", exception.getMessage());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(0)).save(any());
    }

    // Testa a remoção de permissao de admin
    @Test
    void shouldRevokeAdminRole() {
        Role adminRole = createRole("ROLE_ADMIN");
        Role userRole = createRole("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(adminRole, userRole)));

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User savedUsed = userRoleService.revokeAdmin(1L);
        Collection<Role> roleSaved = savedUsed.getRoles();

        assertTrue(roleSaved.contains(userRole));
        assertEquals(1, savedUsed.getRoles().size());

        verify(roleRepository, times(1)).findByName(anyString());
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(1)).save(any());
    }

    // Testa a remocao de admin quando usuario nao existe
    @Test
    void shouldNotRevokeAdminWhenUserNotFound() {
        Role adminRole = createRole("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRoleService.revokeAdmin(1L));

        assertEquals("Usuário nao encontrado", exception.getMessage());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    // Testa a remocao de admin quando o usario já nao tem o cargo de admin
    @Test
    void shouldNotRevokeAdminWhenUserDoesNotHaveAdminRole() {
        Role adminRole = createRole("ROLE_ADMIN");
        Role userRole = createRole("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(userRole)));

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRoleService.revokeAdmin(1L));

        assertEquals("Usuário não possui o cargo de ADMIN!", exception.getMessage());

        verify(roleRepository, times(1)).findByName(anyString());
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).save(any());
    }

    // Testa a permissao de moderador dada ao usuario
    @Test
    void shouldGrantModeratorRole() {
        Role role = createRole("ROLE_MODERATOR");

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(createRole("ROLE_USER"))));

        when(roleRepository.findByName("ROLE_MODERATOR")).thenReturn(Optional.of(role));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User savedUser = userRoleService.grantModerator(1L);
        boolean hasModeratorRole = savedUser
                .getRoles()
                .stream()
                .anyMatch(roleModerator -> roleModerator.getName().equals("ROLE_MODERATOR"));

        assertTrue(hasModeratorRole, "Usuario deveria ter role de ROLE_MODERATOR");
        assertEquals(2, savedUser.getRoles().size());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Testa o caminho de permissao de moderador quando usuario nao existe
    @Test
    void shouldNotGrantModeratorWhenUserNotFound() {
        Role moderatorRole = createRole("ROLE_MODERATOR");

        when(roleRepository.findByName("ROLE_MODERATOR")).thenReturn(Optional.of(moderatorRole));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRoleService.grantModerator(1L));

        assertEquals("Usuário não encontrado!", exception.getMessage());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    // Testa a remocao da permissao de moderador
    @Test
    void shouldRevokeModeratorRole() {
        Role moderatorRole = createRole("ROLE_MODERATOR");
        Role userRole = createRole("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(userRole, moderatorRole)));

        when(roleRepository.findByName("ROLE_MODERATOR")).thenReturn(Optional.of(moderatorRole));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User savedUser = userRoleService.revokeModerator(1L);
        Collection<Role> roles = savedUser.getRoles();

        assertEquals(1, roles.size());
        assertTrue(roles.contains(userRole));

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Testa a remocao da permissao de moderador quando usuario nao existe
    @Test
    void shouldNotRevokeModeratorWhenUserNotFound() {
        Role moderatorRole = createRole("ROLE_MODERATOR");

        when(roleRepository.findByName("ROLE_MODERATOR")).thenReturn(Optional.of(moderatorRole));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRoleService.revokeModerator(1L));

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    // Testa a remocao de permissao de moderador quando usuario ja nao possui ela
    @Test
    void shouldNotRevokeModeratorWhenUserDoesNotHaveModeratorRole() {
        Role moderatorRole = createRole("ROLE_MODERATOR");
        Role userRole = createRole("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(userRole)));

        when(roleRepository.findByName("ROLE_MODERATOR")).thenReturn(Optional.of(moderatorRole));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRoleService.revokeModerator(1L));

        assertEquals("Usuário nao possui o cargo de MODERADOR!", exception.getMessage());

        verify(roleRepository, times(1)).findByName(anyString());
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).save(any());
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setId(1L);
        role.setName(roleName);
        role.setUsers(List.of());

        return role;
    }
}