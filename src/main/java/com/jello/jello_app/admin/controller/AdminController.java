package com.jello.jello_app.admin.controller;

import com.jello.jello_app.common.dto.ApiResponse;
import com.jello.jello_app.role.service.UserRoleService;
import com.jello.jello_app.user.dto.UserDTO;
import com.jello.jello_app.user.mapper.UserMapper;
import com.jello.jello_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/admin")
public class AdminController {

    private final UserRoleService userRoleService;

    @PostMapping("/{userId}/grant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> grantAdmin(@PathVariable Long userId) {
        User user = userRoleService.grantAdmin(userId);

        UserDTO userDto = UserMapper.toDto(user);
        return ResponseEntity.ok(new ApiResponse("User updated to admin!", userDto));
    }

    @PostMapping("/{userId}/revoke")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> revokeAdmin(@PathVariable Long userId) {
        User user = userRoleService.revokeAdmin(userId);

        UserDTO userDto = UserMapper.toDto(user);
        return ResponseEntity.ok(new ApiResponse("Admin promoted to client!", userDto));
    }
}
