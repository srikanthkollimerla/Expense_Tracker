package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class FileUpload {

    @Id
    private String id;
    private String fileName;
    private String fileType;
    private String fileUrl;  // URL or S3 path where the file is stored

    @ManyToOne
    @JoinColumn(name = "transaction_id")  // The foreign key mapping
    private Transaction transaction;

    public FileUpload(String fileName, String fileType, String fileUrl, Integer transactionId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
    }

    public FileUpload() {
    }
}
