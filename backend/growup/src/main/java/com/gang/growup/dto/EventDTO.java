package com.gang.growup.dto;

public class EventDTO {
    private String title;
    private String description;
    private String date;
    private String time;
    private String type;
    
    public EventDTO() {}
    
    public EventDTO(String title, String description, String date, String time, String type) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.type = type;
    }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}