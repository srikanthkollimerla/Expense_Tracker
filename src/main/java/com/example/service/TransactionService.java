package com.example.service;

import com.example.entity.Transaction;
import com.example.entity.User;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a new transaction for a given user.
     */
    public Transaction createTransaction(int userId, Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        transaction.setUser(user);
        return transactionRepository.save(transaction);
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
