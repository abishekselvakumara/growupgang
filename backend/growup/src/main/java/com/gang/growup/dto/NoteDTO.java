package com.gang.growup.dto;

public class NoteDTO {
    private String title;
    private String content;
    private boolean important;
    private String date;
    
    public NoteDTO() {}
    
    public NoteDTO(String title, String content, boolean important, String date) {
        this.title = title;
        this.content = content;
        this.important = important;
        this.date = date;
    }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public boolean isImportant() { return important; }
    public void setImportant(boolean important) { this.important = important; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}