package com.gymtracker.controller;

import com.gymtracker.dto.LoginRequest;
import com.gymtracker.dto.LoginResponse;
import com.gymtracker.dto.RegisterRequest;
import com.gymtracker.dto.ResetPasswordRequest;
import com.gymtracker.entity.User;
import com.gymtracker.security.JwtUtils;
import com.gymtracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(Map.of("message","User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = authService.authenticate(request);
        String token = jwtUtils.generateJwtToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(Map.of("message","Password updated successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String email = jwtUtils.getEmailFromJwtToken(token.substring(7));
        User user = authService.getUserByEmail(email);
        authService.logout(user);
        return ResponseEntity.ok(Map.of("message","Logout successful"));
    }
}
