package deakin.example.itubeapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Insert
    void addToPlaylist(PlaylistItem item);

    @Query("SELECT * FROM PlaylistItem WHERE userId = :userId")
    List<PlaylistItem> getUserPlaylist(int userId);
}
