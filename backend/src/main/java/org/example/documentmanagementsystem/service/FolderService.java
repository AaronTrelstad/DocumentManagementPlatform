package org.example.documentmanagementsystem.service;

import java.util.List;

import org.example.documentmanagementsystem.model.FolderModel;
import org.example.documentmanagementsystem.repository.FolderRepository;
import org.springframework.stereotype.Service;

@Service
public class FolderService {
    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public FolderModel addFolder(String name, String description, List<String> userIds, List<String> documentIds) {
        FolderModel folderModel = new FolderModel(name, description, userIds, documentIds);
        return folderRepository.save(folderModel);
    } 
}
