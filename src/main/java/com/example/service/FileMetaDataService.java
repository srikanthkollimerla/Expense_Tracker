package com.example.service;

import com.example.entity.FileMetadata;
import com.example.entity.Transaction;
import com.example.repository.FileMetaDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileMetaDataService {

    @Autowired
    private FileMetaDataRepository fileMetadataRepository;

    @Transactional
    public boolean upload(FileMetadata fileMetadata) {
        fileMetadataRepository.save(fileMetadata);
        return true;
    }

    public List<FileMetadata> getFiles(Transaction transaction) {
        return fileMetadataRepository.findByTransactionId(transaction.getId());
    }
}
