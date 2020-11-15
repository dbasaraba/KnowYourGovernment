package com.basa.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView currentLocation;
    private LocationManager locationManager;
    private Criteria criteria;
    private final ArrayList<Official> officials = new ArrayList<>();
    private OfficialAdapter oAdapter = new OfficialAdapter(officials, this);
    private RecyclerView recyclerView;
    private String location;

    private static final int MY_LOCATION_REQUEST_CODE_ID = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();
    }

    @Override
    protected void onResume() {
        clear();
        if (networkCheck()) {
            setData();
            currentLocation.setText(location);
        }
        else { mToast("no internet"); }
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                                      grantResults[0] == PERMISSION_GRANTED) {
                setLocation();
            }
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
                if (networkCheck()) {
                    getZipOrLocation();
                }
                break;
            case R.id.menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }

        return super.onOptionsItemSelected(i);
    }

    @Override
    public void onClick(View v) {
        int i = recyclerView.getChildLayoutPosition(v);
        Official o = officials.get(i);
        Intent intent = new Intent(this, OfficialActivity.class);

        intent.putExtra("location", location);
        intent.putExtra("office", o.getOffice());
        intent.putExtra("name", o.getName());
        intent.putExtra("party", o.getParty());
        intent.putExtra("addressLineOne", o.getAddressLineOne());
        intent.putExtra("addressLineTwo", o.getAddressLineTwo());
        intent.putExtra("addressCity", o.getAddressCity());
        intent.putExtra("addressState", o.getAddressState());
        intent.putExtra("addressZip", o.getAddressZip());
        intent.putExtra("phone", o.getPhone());
        intent.putExtra("email", o.getEmail());
        intent.putExtra("website", o.getWebsite());

        startActivity(intent);
    }

    private void setUp() {
        currentLocation = findViewById(R.id.currentLocation);
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
    }

    private boolean networkCheck() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean connection = false;

        if (info != null && info.isConnected()) { connection = true; }
        else { createWarning("Warning", "No Internet Connection"); }

        return connection;
    }

    @SuppressLint("MissingPermission")
    private void setLocation() {
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location currentLocation = null;

        if (bestProvider != null) { currentLocation = locationManager.getLastKnownLocation(bestProvider); }
        if (currentLocation != null) { geoLocation(currentLocation.getLatitude(), currentLocation.getLongitude()); }
    }

    private void getZipOrLocation() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        final EditText txt = new EditText(this);

        txt.setInputType(InputType.TYPE_CLASS_TEXT);
        txt.setGravity(Gravity.CENTER_HORIZONTAL);
        b.setView(txt);

        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                location = txt.getText().toString().toLowerCase();
                onResume();
            }
        });

        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        b.setTitle("Set Location");
        b.setMessage("Enter (zip) or (city, state)");

        AlertDialog dialog = b.create();
        dialog.show();
    }

    private void geoLocation(double lat, double lon) {
        Geocoder geocoder = new Geocoder(this);
        String postalCode = null;

        try {
            List<Address> addresses;
            addresses = geocoder.getFromLocation(lat, lon, 1);
            postalCode = addresses.get(0).getPostalCode();
        }
        catch (IOException e) { mToast(e.getMessage()); }

        if (postalCode == null) { mToast("Couldn't determine location"); }
        else { mToast("Zip " + postalCode); }
    }

    private void setData() {
        Official o1 = new Official();
        o1.setOffice("President");
        o1.setName("George Washington");
        o1.setParty("Republican");
        o1.setAddressLineOne("The White House");
        o1.setAddressLineTwo("1600 Pennsylvania Avenue NW");
        o1.setAddressCity("Washington");
        o1.setAddressState("DC");
        o1.setAddressZip("20500");
        o1.setPhone("(111) 111-1111");
        o1.setEmail("example@example.com");
        o1.setWebsite("www.example.com");
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

    private void clear() {
        officials.clear();
        oAdapter.notifyDataSetChanged();
    }

    private void createWarning(String title, String message) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(message);
        AlertDialog dialog = b.create();
        dialog.show();
    }

    private void mToast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

}
