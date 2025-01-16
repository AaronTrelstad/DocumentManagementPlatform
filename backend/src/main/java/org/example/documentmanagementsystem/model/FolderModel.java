package org.example.documentmanagementsystem.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="folders")
public class FolderModel {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> userIds;
    private List<String> documentIds;
    private LocalDateTime uploadedAt;

    public FolderModel(String name, String description, List<String> userIds, List<String> documentIds) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = name;
        this.userIds = userIds;
        this.documentIds = documentIds;
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

    public List<String> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public void addUserId(String userId) {
        this.userIds.add(userId);
    }

    public void removeUserId(String userId) {
        this.userIds.remove(userId);
    }

    public List<String> getDocumentIds() {
        return this.documentIds;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public void addDocumentId(String documentId) {
        this.documentIds.add(documentId);
    }

    public void removeDocumentId(String documentId) {
        this.documentIds.remove(documentId);
    }

    public LocalDateTime getUploadedAt() {
        return this.uploadedAt;
    }
}
