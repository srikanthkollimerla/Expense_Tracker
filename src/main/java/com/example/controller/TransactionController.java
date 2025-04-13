package com.example.controller;

import com.example.entity.Transaction;
import com.example.entity.FileMetadata;
import com.example.service.TransactionService;
import com.example.service.FileMetaDataService;
import com.example.service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FileMetaDataService fileUploadService;

    @Autowired
    private S3Service s3Service;

    // 1. Create a transaction for a user
    @PostMapping("/user/{userId}")
    public Transaction createTransaction(
            @PathVariable int userId,
            @RequestPart("transaction") String transactionJSON,
            @RequestPart(value = "file", required = false) MultipartFile file) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Transaction transaction = objectMapper.readValue(transactionJSON,Transaction.class);
        return transactionService.createTransaction(userId, transaction, file);
    }

    // 2. Get all transactions for a user
    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable int userId) {
        return transactionService.getTransactionsByUser(userId);
    }

    // 3. Get a transaction by ID
    @GetMapping("/{transactionId}")
    public Transaction getTransactionById(@PathVariable int transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    // 4. Upload a file for a transaction
    @PostMapping("/{transactionId}/upload")
    public FileMetadata uploadFile(
            @PathVariable int transactionId,
            @RequestParam("file") MultipartFile file) {

        Transaction transaction = transactionService.getTransactionById(transactionId);
        String fileUrl = s3Service.uploadFile(file, "transaction_" + transactionId);

        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileUrl(fileUrl);
        metadata.setTransaction(transaction);

        fileUploadService.upload(metadata);
        return metadata;
    }

    // 5. Get all files for a transaction
    @GetMapping("/{transactionId}/files")
    public List<FileMetadata> getFiles(@PathVariable int transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        return fileUploadService.getFiles(transaction);
    }
}
