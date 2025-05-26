package com.example.week7sqliteandroom_workshop_starter_code;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create new advert
        findViewById(R.id.btnCreate).setOnClickListener(v ->
                startActivity(new Intent(this, CreateAdvertActivity.class))
        );

        // Show list of adverts
        findViewById(R.id.btnShowAll).setOnClickListener(v ->
                startActivity(new Intent(this, LostFoundListActivity.class))
        );

        // SHOW ON MAP
        findViewById(R.id.btnShowOnMap).setOnClickListener(v ->
                startActivity(new Intent(this, MapsActivity.class))
        );
    }
}
