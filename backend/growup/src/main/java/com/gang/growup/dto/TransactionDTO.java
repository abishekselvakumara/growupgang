package com.gang.growup.dto;

public class TransactionDTO {
    private String type;
    private String description;
    private Double amount;
    private String category;
    private String date;
    
    public TransactionDTO() {}
    
    public TransactionDTO(String type, String description, Double amount, String category, String date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    
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
}