package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "file_metadata")  // Map this entity to your actual SQL table
@Getter
@Setter
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private long fileSize;

    // Establish the relationship with the Transaction entity
    @ManyToOne
    @JoinColumn(name = "transaction_id")  // The column that links to the Transaction table
    private Transaction transaction;

    public FileMetadata() {}

    // Constructor updated to accept a Transaction object
    public FileMetadata(String fileName, String fileUrl, String fileType, long fileSize, Transaction transaction) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.transaction = transaction;  // Store the Transaction object directly
    }
}
