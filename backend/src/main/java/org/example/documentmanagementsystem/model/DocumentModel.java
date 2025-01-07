package org.example.documentmanagementsystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
public class DocumentModel {
    @Id
    private String id; 
    private String name;
    private String description;
    private String submitterId;
    private String fileId;
    private LocalDateTime uploadedAt;
    private String fileBase64;

    public DocumentModel(String name, String description, String submitterId, String fileId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.submitterId = submitterId;
        this.fileId = fileId;
        this.uploadedAt = LocalDateTime.now();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubmitterId() {
        return this.submitterId;
    }

    public String getFileId() {
        return this.fileId;
    }

    public LocalDateTime getUploadedAt() {
        return this.uploadedAt;
    }

    public String getFileBase64() {
        return fileBase64;
    }

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }
}
