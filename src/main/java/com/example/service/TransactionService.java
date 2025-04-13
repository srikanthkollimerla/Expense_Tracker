package com.example.service;

import com.example.entity.FileMetadata;
import com.example.entity.Transaction;
import com.example.entity.User;
import com.example.repository.FileMetaDataRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final FileMetaDataService fileMetaDataService;
    private final FileMetaDataRepository fileMetaDataRepository;
    private final S3Service s3Service;

    // Constructor to inject the repositories and services
    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              FileMetaDataService fileMetaDataService,
                              FileMetaDataRepository fileMetaDataRepository,
                              S3Service s3Service) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.fileMetaDataService = fileMetaDataService;
        this.fileMetaDataRepository = fileMetaDataRepository;
        this.s3Service = s3Service;
    }

    /**
     * Create a new transaction for a given user and handle file upload to S3.
     */
    public Transaction createTransaction(int userId, Transaction transaction, MultipartFile file) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Set the user for the transaction
        transaction.setUser(user);

        // Save the transaction to the database
        logger.info("Creating transaction for user {}", userId);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Handle file upload if a file is provided
        if (file != null) {
            try {
                // Upload the file to S3 and get the file URL
                String keyPrefix = "transactions/" + userId;
                String fileUrl = s3Service.uploadFile(file, keyPrefix);

                // Create and save the file metadata
                FileMetadata fileMetadata = new FileMetadata();
                fileMetadata.setFileName(file.getOriginalFilename());
                fileMetadata.setFileUrl(fileUrl);
                fileMetadata.setFileType(file.getContentType());
                fileMetadata.setFileSize(file.getSize());
                fileMetadata.setTransaction(savedTransaction);

                // Save the file metadata to the database
                fileMetaDataRepository.save(fileMetadata);

            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file", e);
            }
        }

        return savedTransaction;
    }

    /**
     * Get all transactions for a specific user.
     */
    public List<Transaction> getTransactionsByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return transactionRepository.findByUserId(userId);
    }

    /**
     * Retrieve a transaction by its ID.
     */
    public Transaction getTransactionById(int transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
    }

    /**
     * Delete a transaction by its ID.
     */
    public void deleteTransaction(int transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new RuntimeException("Transaction not found with ID: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }
}
