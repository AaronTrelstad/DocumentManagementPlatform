package org.example.documentmanagementsystem.repository;

import org.example.documentmanagementsystem.model.FolderModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FolderRepository extends MongoRepository<FolderModel, String>{}
