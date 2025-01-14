package org.example.documentmanagementsystem.service;

import org.example.documentmanagementsystem.components.JwtUtils;
import org.example.documentmanagementsystem.model.UserModel;
import org.example.documentmanagementsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel register(String name, String username, String email, String password, String role) {
        UserModel check1 = this.userRepository.findByUsername(username);
        UserModel check2 = this.userRepository.findByEmail(email);

        if (check1 != null || check2 != null) {
            throw new RuntimeException("Invalid credentials");
        }
        String encodedPassword = passwordEncoder.encode(password); 
        UserModel user = new UserModel(name, username, encodedPassword, email, role);

        return userRepository.save(user);
    }

    public String login(String username, String password) {
        UserModel user = this.userRepository.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtUtils.generateToken(username, user.getRole());
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
