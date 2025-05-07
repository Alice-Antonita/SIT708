package deakin.example.newsapp;

import java.io.Serializable;

public class News implements Serializable {
    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private String date;
    private static final long serialVersionUID = 1L;

    public News(int id, String title, String description, String imageUrl, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getDate() { return date; }
}
