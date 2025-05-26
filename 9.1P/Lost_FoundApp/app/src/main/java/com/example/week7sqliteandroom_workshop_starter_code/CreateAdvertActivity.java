package com.example.week7sqliteandroom_workshop_starter_code;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.*;

public class CreateAdvertActivity extends AppCompatActivity {
    private RadioGroup    rgType;
    private RadioButton   rbLost, rbFound;
    private EditText      etName, etPhone, etDescription, etDate, etLocation;
    private Button        btnGetCurrent, btnSave;
    private FusedLocationProviderClient fusedClient;

    // Launchers for permission + autocomplete
    private ActivityResultLauncher<String> permissionLauncher;
    private ActivityResultLauncher<Intent>  placeLauncher;

    // Our item model
    private LostFoundItem currentItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        // Show back arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Places SDK (for autocomplete)
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                    getString(R.string.google_maps_key));
        }

        // Prepare the model
        currentItem = new LostFoundItem();

        // Bind views
        rgType        = findViewById(R.id.rgType);
        rbLost        = findViewById(R.id.rbLost);
        rbFound       = findViewById(R.id.rbFound);
        etName        = findViewById(R.id.etName);
        etPhone       = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate        = findViewById(R.id.etDate);
        etLocation    = findViewById(R.id.etLocation);
        btnGetCurrent = findViewById(R.id.btnGetCurrent);
        btnSave       = findViewById(R.id.btnSave);

        // Date picker
        etDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this,
                    (dp, y, m, d) -> {
                        String date = String.format(Locale.getDefault(),
                                "%04d-%02d-%02d", y, m + 1, d);
                        etDate.setText(date);
                        currentItem.setDate(date);
                    },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        // FusedLocation for “Get Current Location”
        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (Boolean.TRUE.equals(granted)) {
                        fetchCurrentLocation();
                    }
                }
        );
        btnGetCurrent.setOnClickListener(v ->
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        );

        // Autocomplete setup
        placeLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                        String addr = place.getAddress();
                        etLocation.setText(addr);
                        currentItem.setLocation(addr);
                        if (place.getLatLng() != null) {
                            currentItem.setLatitude(place.getLatLng().latitude);
                            currentItem.setLongitude(place.getLatLng().longitude);
                        }
                    }
                }
        );
        etLocation.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG
            );
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fields)
                    .build(this);
            placeLauncher.launch(intent);
        });

        // Save advert
        btnSave.setOnClickListener(v -> {
            // Fill remaining fields
            currentItem.setType(rbLost.isChecked() ? "Lost" : "Found");
            currentItem.setReporterName(etName.getText().toString().trim());
            currentItem.setPhone(etPhone.getText().toString().trim());
            currentItem.setDescription(etDescription.getText().toString().trim());

            // Validate
            if (currentItem.getDescription().isEmpty()
                    || currentItem.getLocation().isEmpty()) {
                Toast.makeText(this,
                        "Description and Location required",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert into DB
            LostFoundDbHelper db = new LostFoundDbHelper(this);
            long id = db.insertItem(currentItem);
            Toast.makeText(this,
                    id > 0 ? "Saved advert #" + id : "Save failed",
                    Toast.LENGTH_SHORT).show();

            finish();
        });
    }

    private void fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {

            fusedClient.getLastLocation()
                    .addOnSuccessListener(loc -> {
                        if (loc != null) {
                            double lat = loc.getLatitude(),
                                    lng = loc.getLongitude();
                            currentItem.setLatitude(lat);
                            currentItem.setLongitude(lng);

                            // Reverse-geocode
                            try {
                                List<Address> list = new Geocoder(
                                        this, Locale.getDefault())
                                        .getFromLocation(lat, lng, 1);
                                if (!list.isEmpty()) {
                                    String address = list.get(0).getAddressLine(0);
                                    etLocation.setText(address);
                                    currentItem.setLocation(address);
                                }
                            } catch (Exception ignored) {}
                        }
                    });
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
