package com.example.repository;

import com.example.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileUploadRepository extends JpaRepository<FileUpload,String> {
    List<FileUpload> findByTransactionId(Integer transactionId);
}
