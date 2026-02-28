package com.gang.growup.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type;
    private String description;
    private Double amount;
    private String category;
    private String date;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Transaction() {}
    
    public Transaction(String type, String description, Double amount, String category, String date, User user) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.user = user;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}