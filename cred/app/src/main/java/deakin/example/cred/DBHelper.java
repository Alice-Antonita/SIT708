package deakin.example.cred;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Constructor: create or open the database
    public DBHelper(Context context) {
        super(context, "TaskManager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        // Create table with task title as the primary key
        DB.execSQL("CREATE TABLE Tasks(title TEXT PRIMARY KEY, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        // Drop table if it exists (for upgrades)
        DB.execSQL("DROP TABLE IF EXISTS Tasks");
        onCreate(DB);
    }

    // Insert a new task
    public boolean insertTask(String title, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);

        long result = DB.insert("Tasks", null, values);
        return result != -1; // return true if insertion succeeded
    }

    // Update an existing task by title
    public boolean updateTask(String title, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description", description);

        Cursor cursor = DB.rawQuery("SELECT * FROM Tasks WHERE title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.update("Tasks", values, "title = ?", new String[]{title});
            return result != -1;
        } else {
            return false;
        }
    }

    // Delete a task by title
    public boolean deleteTask(String title) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM Tasks WHERE title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Tasks", "title = ?", new String[]{title});
            return result != -1;
        } else {
            return false;
        }
    }

    // Get all tasks
    public Cursor getAllTasks() {
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery("SELECT * FROM Tasks", null);
    }
}
