package com.example.week7sqliteandroom_workshop_starter_code;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LostFoundDetailActivity extends AppCompatActivity {
    private TextView tvType, tvName, tvPhone, tvDesc, tvDate, tvLoc;
    private Button btnRemove;
    private long itemId;
    private LostFoundItem current;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvType  = findViewById(R.id.tvType);
        tvName  = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvDesc  = findViewById(R.id.tvDesc);
        tvDate  = findViewById(R.id.tvDate);
        tvLoc   = findViewById(R.id.tvLoc);
        btnRemove = findViewById(R.id.btnRemove);

        itemId = getIntent().getLongExtra("item_id", -1);
        LostFoundDbHelper db = new LostFoundDbHelper(this);
        for (LostFoundItem it : db.getAllItems()) {
            if (it.getId() == itemId) { current = it; break; }
        }
        if (current != null) {
            tvType.setText(current.getType());
            tvName.setText(current.getReporterName());
            tvPhone.setText(current.getPhone());
            tvDesc.setText(current.getDescription());
            tvDate.setText(current.getDate());
            tvLoc.setText(current.getLocation());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}