package org.example.documentmanagementsystem.service;

import java.util.Optional;

import org.example.documentmanagementsystem.model.UserModel;
import org.example.documentmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUser(String id) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()) {
            UserModel existingUser = user.get();

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
    }

    public UserModel updateUser(String id, UserModel updatedUser) { 
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()) {
            UserModel existingUser = user.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setBio(updatedUser.getBio());
            existingUser.setTitle(updatedUser.getTitle());

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
    }
}
