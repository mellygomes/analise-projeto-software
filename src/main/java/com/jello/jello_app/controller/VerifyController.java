package com.jello.jello_app.controller;

import com.jello.jello_app.dto.ApiResponse;
import com.jello.jello_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verify")
public class VerifyController {
    private final UserService userService;

    @GetMapping("/account")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestParam String token) {
        userService.verifyAccountKey(token);
        return ResponseEntity.ok().body(new ApiResponse("Account verified!", null));
    }
}
