package com.example.week7sqliteandroom_workshop_starter_code;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreate  = findViewById(R.id.btnCreate);
        Button btnShowAll = findViewById(R.id.btnShowAll);

        btnCreate.setOnClickListener(v ->
                startActivity(new Intent(this, CreateAdvertActivity.class))
        );

        btnShowAll.setOnClickListener(v ->
                startActivity(new Intent(this, LostFoundListActivity.class))
        );
    }
}
