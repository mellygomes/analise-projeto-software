package com.jello.jello_app.role.service;

import com.jello.jello_app.enumeration.RoleType;
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
        Role role = createRole(RoleType.ROLE_ADMIN.getName());

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(createRole(RoleType.ROLE_USER.getName()))));

        when(roleRepository.findByName(RoleType.ROLE_ADMIN.getName())).thenReturn(Optional.of(role));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User savedUser = userRoleService.grantAdmin(1L);
        boolean hasAdminRole = savedUser.getRoles()
                .stream()
                .anyMatch(roleUser -> roleUser.getName().equals(RoleType.ROLE_ADMIN.getName()));

        assertTrue(hasAdminRole, "O usuario deveria possuir a ROLE_ADMIN");
        assertEquals(2, savedUser.getRoles().size());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
    }

    // Testa o caminho onde tenta dar a permissao quando o usuario nao existe
    @Test
    void shouldNotGrantAdminWhenUserNotFound() {
        Role adminRole = createRole(RoleType.ROLE_ADMIN.getName());

        when(roleRepository.findByName(RoleType.ROLE_ADMIN.getName())).thenReturn(Optional.of(adminRole));
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
        Role adminRole = createRole(RoleType.ROLE_ADMIN.getName());
        Role userRole = createRole(RoleType.ROLE_USER.getName());

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(adminRole, userRole)));

        when(roleRepository.findByName(RoleType.ROLE_ADMIN.getName())).thenReturn(Optional.of(adminRole));
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
        Role adminRole = createRole(RoleType.ROLE_ADMIN.getName());

        when(roleRepository.findByName(RoleType.ROLE_ADMIN.getName())).thenReturn(Optional.of(adminRole));
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
        Role adminRole = createRole(RoleType.ROLE_ADMIN.getName());
        Role userRole = createRole(RoleType.ROLE_USER.getName());

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(userRole)));

        when(roleRepository.findByName(RoleType.ROLE_ADMIN.getName())).thenReturn(Optional.of(adminRole));
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
        Role role = createRole(RoleType.ROLE_MODERATOR.getName());

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(createRole(RoleType.ROLE_USER.getName()))));

        when(roleRepository.findByName(RoleType.ROLE_MODERATOR.getName())).thenReturn(Optional.of(role));
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
                .anyMatch(roleModerator -> roleModerator.getName().equals(RoleType.ROLE_MODERATOR.getName()));

        assertTrue(hasModeratorRole, "Usuario deveria ter role de ROLE_MODERATOR");
        assertEquals(2, savedUser.getRoles().size());

        verify(roleRepository, times(1)).findByName(any(String.class));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Testa o caminho de permissao de moderador quando usuario nao existe
    @Test
    void shouldNotGrantModeratorWhenUserNotFound() {
        Role moderatorRole = createRole(RoleType.ROLE_MODERATOR.getName());

        when(roleRepository.findByName(RoleType.ROLE_MODERATOR.getName())).thenReturn(Optional.of(moderatorRole));
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
        Role moderatorRole = createRole(RoleType.ROLE_MODERATOR.getName());
        Role userRole = createRole(RoleType.ROLE_USER.getName());

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(userRole, moderatorRole)));

        when(roleRepository.findByName(RoleType.ROLE_MODERATOR.getName())).thenReturn(Optional.of(moderatorRole));
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
        Role moderatorRole = createRole(RoleType.ROLE_MODERATOR.getName());

        when(roleRepository.findByName(RoleType.ROLE_MODERATOR.getName())).thenReturn(Optional.of(moderatorRole));
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
        Role moderatorRole = createRole(RoleType.ROLE_MODERATOR.getName());
        Role userRole = createRole(RoleType.ROLE_USER.getName());

        User user = new User();
        user.setId(1L);
        user.setRoles(new HashSet<>(Set.of(userRole)));

        when(roleRepository.findByName(RoleType.ROLE_MODERATOR.getName())).thenReturn(Optional.of(moderatorRole));
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