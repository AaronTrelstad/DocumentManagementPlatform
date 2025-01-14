package org.example.documentmanagementsystem.repository;
import org.example.documentmanagementsystem.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByUsername(String username);
    UserModel findByEmail(String email);
}
