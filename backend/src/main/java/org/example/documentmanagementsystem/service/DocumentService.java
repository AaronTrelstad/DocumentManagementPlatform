package org.example.documentmanagementsystem.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
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

    public DocumentModel addDocument(String name, String description, String submitterId, MultipartFile file) throws IOException {
        var fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString();
        DocumentModel documentEntity = new DocumentModel(name, description, submitterId, fileId);
        return documentRepository.save(documentEntity);
    }

    public List<DocumentModel> fetchDocuments() throws IOException {
        List<DocumentModel> documents = documentRepository.findAll();

        for (DocumentModel document : documents) {
            ObjectId fileId = new ObjectId(document.getFileId());

            Query query = new Query(Criteria.where("_id").is(fileId));

            GridFSFile gridFSFile = gridFsTemplate.find(query).first(); 

            if (gridFSFile != null) {
                GridFsResource resource = gridFsTemplate.getResource(gridFSFile);

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
        Optional<DocumentModel> document = documentRepository.findById(id);
        if (document.isPresent()) {
            documentRepository.delete(document.get());
            return "Document deleted successfully";
        } else {
            return "No document found with the given ID";
        }
    }
}
