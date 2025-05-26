package com.example.week7sqliteandroom_workshop_starter_code;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LostFoundDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        db = new LostFoundDbHelper(this);
        SupportMapFragment mapFrag = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<LostFoundItem> items = db.getAllItems();
        if (items.isEmpty()) {
            // Fallback to Deakin Burwood if no adverts
            LatLng deakin = new LatLng(-37.8501, 145.1164);
            mMap.addMarker(new MarkerOptions()
                    .position(deakin)
                    .title("Deakin University Burwood"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deakin, 15f));
            Toast.makeText(this,
                    "No adverts found. Showing Deakin Burwood",
                    Toast.LENGTH_SHORT).show();
        } else {
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            for (LostFoundItem it : items) {
                double lat = it.getLatitude();
                double lng = it.getLongitude();
                // Only plot if valid coordinates
                if (lat != 0.0 || lng != 0.0) {
                    LatLng pos = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title(it.getType() + ": " + it.getDescription()));
                    bounds.include(pos);
                }
            }
            // zoom to fit all pins
            if (!items.isEmpty()) {
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngBounds(bounds.build(), 100)
                );
            }
        }
    }
}
