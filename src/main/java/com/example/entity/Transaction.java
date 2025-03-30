package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;
    private String category;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")  // This establishes the link between Transaction and User
    private User user;

    // One-to-many relationship: One transaction can have multiple files
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FileUpload> fileUploads;

    public Transaction() {}

    public Transaction(double amount, String category, String notes, User user) {
        this.amount = amount;
        this.category = category;
        this.notes = notes;
        this.user = user;
    }
}
