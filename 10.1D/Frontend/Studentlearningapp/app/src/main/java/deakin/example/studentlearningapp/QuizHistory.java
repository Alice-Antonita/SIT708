package deakin.example.studentlearningapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import java.util.List;

@Entity(tableName = "quiz_history")
public class QuizHistory {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String question;
    public String yourAnswer;
    public String correctAnswer;
    public long timestamp;
}

@Dao
interface QuizHistoryDao {
    @Insert
    long insert(QuizHistory record);

    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    List<QuizHistory> getAll();

    @Query("DELETE FROM quiz_history")
    void clearAll();
}

@androidx.room.Database(entities = {QuizHistory.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    public abstract QuizHistoryDao historyDao();

    private static volatile AppDatabase instance;
    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}