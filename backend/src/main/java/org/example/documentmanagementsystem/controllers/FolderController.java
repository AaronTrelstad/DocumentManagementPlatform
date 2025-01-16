package org.example.documentmanagementsystem.controllers;

import java.util.List;

import org.example.documentmanagementsystem.model.FolderModel;
import org.example.documentmanagementsystem.service.FolderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addFolder(
        @RequestParam String name,
        @RequestParam String description,
        @RequestParam List<String> userIds,
        @RequestParam List<String> documentIds
    ) {
        try {
            FolderModel folder = this.folderService.addFolder(name, description, userIds, documentIds);
            return ResponseEntity.ok(folder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
