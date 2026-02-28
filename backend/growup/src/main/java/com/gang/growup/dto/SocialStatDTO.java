package com.gang.growup.dto;

public class SocialStatDTO {
    private String platform;
    private String source;
    private Integer value;
    private String label;
    private String note;
    
    public SocialStatDTO() {}
    
    public SocialStatDTO(String platform, String source, Integer value, String label, String note) {
        this.platform = platform;
        this.source = source;
        this.value = value;
        this.label = label;
        this.note = note;
    }
    
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }
    
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}