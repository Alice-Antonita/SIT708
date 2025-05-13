package com.example.week7sqliteandroom_workshop_starter_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LostFoundListActivity extends AppCompatActivity implements ItemAdapter.OnClickListener {
    private RecyclerView rvItems;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvItems = findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        loadData();
        findViewById(R.id.fabAdd).setOnClickListener(v ->
                startActivity(new Intent(this, CreateAdvertActivity.class))
        );
    }

    private void loadData() {
        LostFoundDbHelper db = new LostFoundDbHelper(this);
        List<LostFoundItem> all = db.getAllItems();
        adapter = new ItemAdapter(all, this);
        rvItems.setAdapter(adapter);
    }

    @Override
    public void onItemClick(LostFoundItem item) {
        Intent i = new Intent(this, LostFoundDetailActivity.class);
        i.putExtra("item_id", item.getId());
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();  // refresh list after create/delete
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