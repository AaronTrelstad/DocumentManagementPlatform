package org.example.documentmanagementsystem.repository;
import org.example.documentmanagementsystem.model.DocumentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<DocumentModel, String> {}
