package org.example.documentmanagementsystem.controllers;

import org.example.documentmanagementsystem.model.LoginModel;
import org.example.documentmanagementsystem.model.UserModel;
import org.example.documentmanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserModel data
    ) {
        UserModel user = this.authService.register(
            data.getName(),
            data.getUsername(),
            data.getEmail(),
            data.getPassword(),
            data.getRole()
        );

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
        @RequestBody LoginModel data
    ) {
        String token = this.authService.login(data.getUsername(), data.getPassword());

        return ResponseEntity.ok(token);
    }
}
