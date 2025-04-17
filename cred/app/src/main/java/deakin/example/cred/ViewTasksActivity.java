package deakin.example.cred;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewTasksActivity extends AppCompatActivity {

    ListView listView;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("View Tasks");
        }
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
        listView = findViewById(R.id.listView);
        DB = new DBHelper(this);

        loadTasks();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void loadTasks() {
        Cursor cursor = DB.getAllTasks();
        ArrayList<String> tasks = new ArrayList<>();

        if (cursor.getCount() == 0) {
            tasks.add("No tasks found.");
        } else {
            while (cursor.moveToNext()) {
                String title = cursor.getString(0);
                String desc = cursor.getString(1);
                tasks.add("Title: " + title + "\nDescription: " + desc);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(adapter);
    }
}
