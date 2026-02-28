package com.gang.growup.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class DiaryDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate date;
    private LocalTime time;
    private String formattedDate;

    // Default constructor
    public DiaryDTO() {}

    // Constructor with fields
    public DiaryDTO(Long id, String title, String content, LocalDate date, LocalTime time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public String getFormattedDate() { return formattedDate; }
    public void setFormattedDate(String formattedDate) { this.formattedDate = formattedDate; }
}