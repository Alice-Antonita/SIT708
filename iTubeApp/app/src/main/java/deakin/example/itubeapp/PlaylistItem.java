package deakin.example.itubeapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlaylistItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public String videoUrl;

    public PlaylistItem(int userId, String videoUrl) {
        this.userId = userId;
        this.videoUrl = videoUrl;
    }
}
