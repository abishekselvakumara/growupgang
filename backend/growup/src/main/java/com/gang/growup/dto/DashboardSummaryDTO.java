package com.gang.growup.dto;

public class DashboardSummaryDTO {
    private Double totalIncome;
    private Double totalExpenses;
    private Double savings;
    private Integer totalNotes;
    private Integer importantNotes;
    private Integer totalEvents;
    private Integer upcomingEvents;
    private Integer codingStreak;
    private Integer weeklyActive;
    
    public Double getTotalIncome() { return totalIncome; }
    public void setTotalIncome(Double totalIncome) { this.totalIncome = totalIncome; }
    
    public Double getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(Double totalExpenses) { this.totalExpenses = totalExpenses; }
    
    public Double getSavings() { return savings; }
    public void setSavings(Double savings) { this.savings = savings; }
    
    public Integer getTotalNotes() { return totalNotes; }
    public void setTotalNotes(Integer totalNotes) { this.totalNotes = totalNotes; }
    
    public Integer getImportantNotes() { return importantNotes; }
    public void setImportantNotes(Integer importantNotes) { this.importantNotes = importantNotes; }
    
    public Integer getTotalEvents() { return totalEvents; }
    public void setTotalEvents(Integer totalEvents) { this.totalEvents = totalEvents; }
    
    public Integer getUpcomingEvents() { return upcomingEvents; }
    public void setUpcomingEvents(Integer upcomingEvents) { this.upcomingEvents = upcomingEvents; }
    
    public Integer getCodingStreak() { return codingStreak; }
    public void setCodingStreak(Integer codingStreak) { this.codingStreak = codingStreak; }
    
    public Integer getWeeklyActive() { return weeklyActive; }
    public void setWeeklyActive(Integer weeklyActive) { this.weeklyActive = weeklyActive; }
}