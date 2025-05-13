package com.example.week7sqliteandroom_workshop_starter_code;

public class LostFoundItem {
    private long id;
    private String type;
    private String reporterName;
    private String phone;
    private String description;
    private String date;
    private String location;

    public LostFoundItem() { }

    public LostFoundItem(String type,
                         String reporterName,
                         String phone,
                         String description,
                         String date,
                         String location) {
        this.type = type;
        this.reporterName = reporterName;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}