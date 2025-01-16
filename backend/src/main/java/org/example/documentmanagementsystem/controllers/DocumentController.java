package org.example.documentmanagementsystem.controllers;

import java.io.IOException;
import java.util.List;

import org.example.documentmanagementsystem.model.DocumentModel;
import org.example.documentmanagementsystem.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/")
    public ResponseEntity<?> uploadDocument(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String submitterId,
            @RequestParam MultipartFile file,
            @RequestParam String folderId,
            @RequestParam int likes
    ) {
        try {
            DocumentModel document = this.documentService.addDocument(name, description, submitterId, file, folderId, likes);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<DocumentModel>> getAllDocuments() throws IOException {
        List<DocumentModel> documents = this.documentService.fetchDocuments();
        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable String id) {
        String result = this.documentService.deleteDocumentById(id);

        if (result.equals("Document deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editDocument(
        @PathVariable String id, 
        @RequestParam String name, 
        @RequestParam String description
    ) {
        DocumentModel document = this.documentService.editDocument(id, name, description);

        if (document != null) {
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.status(404).body("Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocument(
        @PathVariable String id
    ) {
        DocumentModel document = this.documentService.getDocument(id);

        if (document != null) {
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.status(404).body("Error");
        }
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity<?> likeDocument(
        @PathVariable String id
    ) {
        DocumentModel document = this.documentService.likeDocument(id);

        if (document != null) {
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.status(404).body("Error");
        }
    }

    @PatchMapping("/{id}/view")
    public ResponseEntity<?> viewDocument(
        @PathVariable String id
    ) {
        System.out.println("Adding the views.");
        DocumentModel document = this.documentService.viewDocument(id);

        if (document != null) {
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.status(404).body("Error");
        }
    }

    @PatchMapping("/{id}/comment")
    public ResponseEntity<?> addComment(
        @PathVariable String id,
        @RequestParam String commenterId,
        @RequestParam String message
    ) {
        DocumentModel document = this.documentService.addComment(id, commenterId, message);

        if (document != null) {
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.status(404).body("Error");
        }
    }

    @GetMapping("/{id}/documents")
    public ResponseEntity<?> getDocumentsBySubmitterId(
        @PathVariable String id
    ) throws IOException {
        List<DocumentModel> documents = this.documentService.getDocumentsBySubmitterId(id);

        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }
}
