package com.jello.jello_app.admin.controller;

import com.jello.jello_app.common.dto.ApiResponse;
import com.jello.jello_app.user.dto.UserDTO;
import com.jello.jello_app.user.model.User;
import com.jello.jello_app.user.service.UserService;
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
    private final UserService userService;

    @PostMapping("/{userId}/grant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> grantAdmin(@PathVariable Long userId){
        User user = userService.grantAdmin(userId);

        UserDTO userDto = userService.userDtoBuilder(user);
        return ResponseEntity.ok(new ApiResponse("User updated to admin!", userDto));
    }

    @PostMapping("/{userId}/revoke")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> revokeAdmin(@PathVariable Long userId){
        User user = userService.revokeAdmin(userId);

        UserDTO userDto = userService.userDtoBuilder(user);
        return ResponseEntity.ok(new ApiResponse("Admin promoted to client!", userDto));
    }
}
