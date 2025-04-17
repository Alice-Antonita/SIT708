// MainActivity.java
package deakin.example.cred;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText taskTitle, taskDescription;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = taskTitle.getText().toString();
                String description = taskDescription.getText().toString();

                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean checkInsert = DB.insertTask(title, description);
                Toast.makeText(MainActivity.this, checkInsert ? "Task Added" : "Failed to Add Task", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = taskTitle.getText().toString();
                String description = taskDescription.getText().toString();

                Boolean checkUpdate = DB.updateTask(title, description);
                Toast.makeText(MainActivity.this, checkUpdate ? "Task Updated" : "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = taskTitle.getText().toString();
                Boolean checkDelete = DB.deleteTask(title);
                Toast.makeText(MainActivity.this, checkDelete ? "Task Deleted" : "Delete Failed", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewTasksActivity.class));
            }
        });
    }
}
