package com.happytohelp.homeloan.service;


import com.happytohelp.homeloan.exception.BadRequestException;
import com.happytohelp.homeloan.model.DocumentType;
import com.happytohelp.homeloan.model.LoanApplication;
import com.happytohelp.homeloan.model.LoanDocument;
import com.happytohelp.homeloan.repo.LoanDocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    private final LoanDocumentRepository docRepo;

    public DocumentService(LoanDocumentRepository docRepo) {
        this.docRepo = docRepo;
    }

    public LoanDocument upload(LoanApplication app, DocumentType type, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new BadRequestException("File is required");

        try {
            File base = new File(uploadDir + File.separator + app.getUser().getId() + File.separator + app.getId());
            if (!base.exists()) base.mkdirs();

            String safeName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(base, safeName);

            Files.copy(file.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            LoanDocument d = new LoanDocument();
            d.setLoanApplication(app);
            d.setType(type);
            d.setOriginalFileName(file.getOriginalFilename());
            d.setStoredPath(dest.getAbsolutePath());
            return docRepo.save(d);
        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage(), e);
        }
    }
}