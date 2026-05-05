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

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/moderator")
public class ModeratorController {
    private final UserService userService;

    @PostMapping("/{userId}/grant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> grantModerator(@PathVariable Long userId){
        User user = userService.grantModerator(userId);

        UserDTO userDto = userService.userDtoBuilder(user);
        return ResponseEntity.ok(new ApiResponse("User updated to moderator!", userDto));
    }

    @PostMapping("/{userId}/revoke")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> revokeModerator(@PathVariable Long userId){
        User user = userService.revokeModerator(userId);

        UserDTO userDto = userService.userDtoBuilder(user);
        return ResponseEntity.ok(new ApiResponse("Moderator updated to default user!", userDto));
    }
}
