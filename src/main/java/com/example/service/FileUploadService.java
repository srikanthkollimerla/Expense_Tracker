package com.example.service;

import com.example.entity.FileUpload;
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
    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public boolean upload(FileUpload fileUpload){
        fileUploadRepository.save(fileUpload);
        return true;
    }

    public List<FileUpload> getFiles(Transaction transaction) {
        //return null;
        return fileUploadRepository.findByTransactionId(transaction.getId());
    }

}
