package com.jello.jello_app.controller;

import com.jello.jello_app.dto.ApiResponse;
import com.jello.jello_app.dto.UserDTO;
import com.jello.jello_app.model.User;
import com.jello.jello_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @PostMapping("/{userId}/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> removeAdmin(@PathVariable Long userId){
        User user = userService.removeAdmin(userId);

        UserDTO userDto = userService.userDtoBuilder(user);
        return ResponseEntity.ok(new ApiResponse("Admin promoted to client!", userDto));
    }
}
