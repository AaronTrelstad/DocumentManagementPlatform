package org.example.documentmanagementsystem.model;

import org.springframework.data.annotation.Id;

public class CommentModel {
    @Id
    private String id;
    private String commenterId;
    private String message;

    public CommentModel(String commenterId, String message) {
        this.commenterId = commenterId;
        this.message = message;
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
}
