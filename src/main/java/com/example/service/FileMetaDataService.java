
package com.example.service;

import com.example.entity.FileMetadata;
import com.example.entity.Transaction;
import com.example.repository.FileUploadRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Transactional
    public boolean upload(FileMetadata fileUpload){
        fileUploadRepository.save(fileUpload);
        return true;
    }

    public List<FileMetadata> getFiles(Transaction transaction) {
        //return null;
        return fileUploadRepository.findByTransactionId(transaction.getId());
    }

}
