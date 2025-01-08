package org.example.documentmanagementsystem.controllers;
import java.io.IOException;
import java.util.List;

import org.example.documentmanagementsystem.model.DocumentModel;
import org.example.documentmanagementsystem.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam String folderId
    ) {
        try {
            DocumentModel document = documentService.addDocument(name, description, submitterId, file, folderId);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DocumentModel>> getAllDocuments() throws IOException {
        List<DocumentModel> documents = documentService.fetchDocuments();
        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable String id) {
        String result = documentService.deleteDocumentById(id);
        
        if (result.equals("Document deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }
}
