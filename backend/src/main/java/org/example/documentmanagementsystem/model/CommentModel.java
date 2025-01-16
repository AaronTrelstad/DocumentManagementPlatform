package org.example.documentmanagementsystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class CommentModel {
    @Id
    private String id;
    private String commenterId;
    private String message;
    private LocalDateTime uploadedAt;

    public CommentModel(String commenterId, String message) {
        this.id = UUID.randomUUID().toString();
        this.commenterId = commenterId;
        this.message = message;
        this.uploadedAt = LocalDateTime.now();
    }

    public String getId() {
        return this.id;
    }

    public String getCommenterId() {
        return this.commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getUploadedAt() {
        return this.uploadedAt;
    }
}
