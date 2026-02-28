package com.gang.growup.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String content;
    private boolean important;
    private String date;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Note() {}
    
    public Note(String title, String content, boolean important, String date, User user) {
        this.title = title;
        this.content = content;
        this.important = important;
        this.date = date;
        this.user = user;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public boolean isImportant() { return important; }
    public void setImportant(boolean important) { this.important = important; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}