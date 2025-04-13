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

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private long transactionId;

    public FileMetadata() {}

    public FileMetadata(String fileName, String fileUrl, String fileType, long fileSize, long transactionId) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.transactionId = transactionId;
    }
}
