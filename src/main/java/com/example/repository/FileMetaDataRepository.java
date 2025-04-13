
package com.example.repository;

import com.example.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileMetaDataRepository extends JpaRepository<FileMetadata,String> {
    List<FileMetadata> findByTransactionId(Integer transactionId);
}
