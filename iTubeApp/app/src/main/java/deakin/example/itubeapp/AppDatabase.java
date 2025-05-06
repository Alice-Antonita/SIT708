package deakin.example.itubeapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import deakin.example.itubeapp.User;
import deakin.example.itubeapp.PlaylistItem;

@Database(entities = {User.class, PlaylistItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract PlaylistDao playlistDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "itube_db"
            ).build();
        }
        return instance;
    }
}
