package org.example.documentmanagementsystem.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.example.documentmanagementsystem.model.CommentModel;
import org.example.documentmanagementsystem.model.DocumentModel;
import org.example.documentmanagementsystem.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, GridFsTemplate gridFsTemplate) {
        this.documentRepository = documentRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    public DocumentModel addDocument(String name, String description, String submitterId, MultipartFile file, String folderId, int likes) throws IOException {
        var fileId = this.gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString();
        DocumentModel documentModel = new DocumentModel(name, description, submitterId, fileId, folderId, likes);
        return this.documentRepository.save(documentModel);
    }

    public List<DocumentModel> fetchDocuments() throws IOException {
        List<DocumentModel> documents = this.documentRepository.findAll();

        for (DocumentModel document : documents) {
            ObjectId fileId = new ObjectId(document.getFileId());

            Query query = new Query(Criteria.where("_id").is(fileId));

            GridFSFile gridFSFile = this.gridFsTemplate.find(query).first();

            if (gridFSFile != null) {
                GridFsResource resource = this.gridFsTemplate.getResource(gridFSFile);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    IOUtils.copy(resource.getInputStream(), outputStream);
                    byte[] fileBytes = outputStream.toByteArray();

                    String base64File = Base64.getEncoder().encodeToString(fileBytes);
                    document.setFileBase64(base64File);
                } finally {
                    outputStream.close();
                }
            }
        }

        return documents;
    }

    public String deleteDocumentById(String id) {
        Optional<DocumentModel> document = this.documentRepository.findById(id);
        if (document.isPresent()) {
            documentRepository.delete(document.get());
            return "Document deleted successfully";
        } else {
            return "No document found with the given ID";
        }
    }

    public DocumentModel editDocument(String id, String name, String description) {
        Optional<DocumentModel> optionalDocument = this.documentRepository.findById(id);

        if (optionalDocument.isPresent()) {
            DocumentModel document = optionalDocument.get();
            document.setName(name);
            document.setDescription(description);
            return documentRepository.save(document);
        } else {
            return null;
        }
    }

    public DocumentModel getDocument(String id) {
        Optional<DocumentModel> optionalDocument = this.documentRepository.findById(id);

        if (optionalDocument.isPresent()) {
            DocumentModel document = optionalDocument.get();
            return document;
        } else {
            return null;
        }
    }

    public DocumentModel likeDocument(String id) {
        Optional<DocumentModel> optionalDocument = this.documentRepository.findById(id);

        if (optionalDocument.isPresent()) {
            DocumentModel document = optionalDocument.get();
            int currentLikes = document.getLikes();
            document.setLikes(currentLikes + 1);
            return this.documentRepository.save(document);
        } else {
            return null;
        }
    }

    public DocumentModel viewDocument(String id) {
        Optional<DocumentModel> optionalDocument = this.documentRepository.findById(id);

        if (optionalDocument.isPresent()) {
            DocumentModel document = optionalDocument.get();
            int currentViews = document.getViews();
            document.setViews(currentViews + 1);
            return this.documentRepository.save(document);
        } else {
            return null;
        }
    }

    public DocumentModel addComment(String id, String commenterId, String message) {
        Optional<DocumentModel> optionalDocument = this.documentRepository.findById(id);

        if (optionalDocument.isPresent()) {
            DocumentModel document = optionalDocument.get();
            List<CommentModel> comments = document.getComments();

            CommentModel newComment = new CommentModel(commenterId, message);
            comments.add(newComment);

            document.setComments(comments);

            return this.documentRepository.save(document);
        } else {
            return null;
        }
    }

    public List<DocumentModel> getDocumentsBySubmitterId(String id) throws IOException {
        List<DocumentModel> documents = this.documentRepository.findBySubmitterId(id);

        for (DocumentModel document : documents) {
            ObjectId fileId = new ObjectId(document.getFileId());

            Query query = new Query(Criteria.where("_id").is(fileId));

            GridFSFile gridFSFile = this.gridFsTemplate.find(query).first();

            if (gridFSFile != null) {
                GridFsResource resource = this.gridFsTemplate.getResource(gridFSFile);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    IOUtils.copy(resource.getInputStream(), outputStream);
                    byte[] fileBytes = outputStream.toByteArray();

                    String base64File = Base64.getEncoder().encodeToString(fileBytes);
                    document.setFileBase64(base64File);
                } finally {
                    outputStream.close();
                }
            }
        }

        return documents;
    }
}
