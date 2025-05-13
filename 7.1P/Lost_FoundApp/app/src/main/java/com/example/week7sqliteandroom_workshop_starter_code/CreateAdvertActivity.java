package com.example.week7sqliteandroom_workshop_starter_code;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class CreateAdvertActivity extends AppCompatActivity {
    private RadioGroup rgType;
    private EditText etName, etPhone, etDescription, etDate, etLocation;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rgType = findViewById(R.id.rgType);
        etName  = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription= findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation= findViewById(R.id.etLocation);
        btnSave  = findViewById(R.id.btnSave);

        // date picker for etDate
        etDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this,
                    (DatePicker dp, int y, int m, int d) ->
                            etDate.setText(String.format("%04d-%02d-%02d", y, m+1, d)),
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        btnSave.setOnClickListener(v -> {
            String type = rgType.getCheckedRadioButtonId() == R.id.rbLost ? "Lost" : "Found";
            LostFoundItem item = new LostFoundItem(
                    type,
                    etName.getText().toString(),
                    etPhone.getText().toString(),
                    etDescription.getText().toString(),
                    etDate.getText().toString(),
                    etLocation.getText().toString()
            );
            LostFoundDbHelper db = new LostFoundDbHelper(this);
            db.insertItem(item);
            finish();  // back to list
        });
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