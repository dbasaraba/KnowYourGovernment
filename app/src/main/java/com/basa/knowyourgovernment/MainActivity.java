package com.basa.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private LocationManager locationManager;
    private Criteria criteria;
    private final ArrayList<Official> officials = new ArrayList<>();
    private OfficialAdapter oAdapter = new OfficialAdapter(officials, this);
    private RecyclerView recyclerView;

    private static final int MY_LOCATION_REQUEST_CODE_ID = 111;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    MY_LOCATION_REQUEST_CODE_ID
            );
        }
        else { setLocation(); }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(oAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Official o1 = new Official();
        o1.setOffice("President");
        o1.setName("George Washington");
        o1.setParty("America");
        officials.add(o1);

        Official o2 = new Official();
        o2.setOffice("Vice President");
        o2.setName("The Rock");
        o2.setParty("America");
        officials.add(o2);

        Official o3 = new Official();
        o3.setOffice("Speaker of the House");
        o3.setName("Tony the Tiger");
        o3.setParty("America");
        officials.add(o3);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                grantResults[0] == PERMISSION_GRANTED) { setLocation(); }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu_main, m);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        switch (i.getItemId()) {
            case R.id.menu_search:
                mToast("search");
                break;
            case R.id.menu_about:
                mToast("about");
                break;
        }

        return super.onOptionsItemSelected(i);
    }

    @Override
    public void onClick(View v) {
        mToast("short click");
    }

    @Override
    public boolean onLongClick(View v) {
        mToast("long click");
        return true;
    }

    @SuppressLint("MissingPermission")
    private void setLocation() {
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location currentLocation = null;

        if (bestProvider != null) { currentLocation = locationManager.getLastKnownLocation(bestProvider); }
        if (currentLocation != null) {
            lat = currentLocation.getLatitude();
            lon = currentLocation.getLongitude();
        }
    }

    private void mToast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

}
