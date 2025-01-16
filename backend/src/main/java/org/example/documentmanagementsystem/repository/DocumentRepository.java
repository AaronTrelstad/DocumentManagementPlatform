package org.example.documentmanagementsystem.repository;
import java.util.List;

import org.example.documentmanagementsystem.model.DocumentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<DocumentModel, String> {
    List<DocumentModel> findBySubmitterId(String id);
}
