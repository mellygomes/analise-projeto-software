package com.jello.jello_app.auth.controller;

import com.jello.jello_app.auth.service.AuthService;
import com.jello.jello_app.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verify")
public class VerifyController {

    private final AuthService authService;

    @GetMapping("/account")
    public ResponseEntity<ApiResponse> verifyAccount(@RequestParam String token) {
        try {
            authService.verifyAccountKey(token);
            return ResponseEntity.ok().body(new ApiResponse("Account verified!", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Account not found!", null));
        }
    }
}
