package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;
    private String fileUrl; // S3 URL or key to access the file
    private String fileType;
    private long fileSize;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public FileUpload() {}

    public FileUpload(String fileName, String fileUrl, String fileType, long fileSize, Transaction transaction) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.transaction = transaction;
    }
}
