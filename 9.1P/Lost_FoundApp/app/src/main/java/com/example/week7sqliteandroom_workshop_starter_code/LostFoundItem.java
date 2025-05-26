package com.example.week7sqliteandroom_workshop_starter_code;

public class LostFoundItem {
    private long   id;
    private String type, reporterName, phone, description, date, location;
    private double latitude, longitude;

    public LostFoundItem() { }

    public long   getId()             { return id; }
    public String getType()           { return type; }
    public String getReporterName()   { return reporterName; }
    public String getPhone()          { return phone; }
    public String getDescription()    { return description; }
    public String getDate()           { return date; }
    public String getLocation()       { return location; }
    public double getLatitude()       { return latitude; }
    public double getLongitude()      { return longitude; }

    public void setId(long id)                      { this.id = id; }
    public void setType(String type)                { this.type = type; }
    public void setReporterName(String name)        { this.reporterName = name; }
    public void setPhone(String phone)              { this.phone = phone; }
    public void setDescription(String desc)         { this.description = desc; }
    public void setDate(String date)                { this.date = date; }
    public void setLocation(String loc)             { this.location = loc; }
    public void setLatitude(double lat)             { this.latitude = lat; }
    public void setLongitude(double lng)            { this.longitude = lng; }
}
