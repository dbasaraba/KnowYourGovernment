package com.basa.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class OfficialActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    TextView currentLocation;
    TextView office;
    TextView name;
    TextView party;
    TextView address;
    TextView phone;
    TextView email;
    TextView website;
    ImageView avatar;
    ImageView logo;
    ImageView facebook;
    ImageView twitter;
    ImageView youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        setUp();
        setData();
        makeLinks();
        setColorAndLogo(getIntent().getStringExtra("party"));
    }

    private void setUp() {
        constraintLayout = findViewById(R.id.scrollableConstraint);
        currentLocation = findViewById(R.id.currentLocation);
        office = findViewById(R.id.office);
        name = findViewById(R.id.name);
        party = findViewById(R.id.party);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        website = findViewById(R.id.website);
        avatar = findViewById(R.id.avatar);
        logo = findViewById(R.id.partyLogo);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        youtube = findViewById(R.id.youtube);
    }

    private void makeLinks() {
        Linkify.addLinks(address, Linkify.ALL); address.setLinkTextColor(Color.WHITE);
        Linkify.addLinks(phone, Linkify.ALL); phone.setLinkTextColor(Color.WHITE);
        Linkify.addLinks(email, Linkify.ALL); email.setLinkTextColor(Color.WHITE);
        Linkify.addLinks(website, Linkify.ALL); website.setLinkTextColor(Color.WHITE);
    }

    private void setData() {
        String partyText = '(' + getIntent().getStringExtra("party") + ')';
        String addressLineOne = getIntent().getStringExtra("addressLineOne");
        String city = getIntent().getStringExtra("addressCity");
        String state = getIntent().getStringExtra("addressState");
        String zip = getIntent().getStringExtra("addressZip");
        String fullAddress = addressLineOne + '\n'  + city + ", " + state + ' ' + zip;

        currentLocation.setText(getIntent().getStringExtra("location"));
        office.setText(getIntent().getStringExtra("office"));
        name.setText(getIntent().getStringExtra("name"));
        party.setText(partyText);
        address.setText(fullAddress);
        phone.setText(getIntent().getStringExtra("phone"));
        email.setText(getIntent().getStringExtra("email"));
        website.setText(getIntent().getStringExtra("website"));
    }

    private void setColorAndLogo(String s) {
        if (Objects.equals(s, "Democratic")) {
            constraintLayout.setBackgroundColor(Color.BLUE);
            logo.setImageResource(R.drawable.dem_logo);
        }
        else if (Objects.equals(s, "Republican")) {
            constraintLayout.setBackgroundColor(Color.RED);
            logo.setImageResource(R.drawable.rep_logo);
        }
        else { constraintLayout.setBackgroundColor(Color.BLACK); }
    }

    private void mToast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

    public void onAvatar(View v) {
        Intent intent = new Intent(this, PhotoActivity.class);

        intent.putExtra("location", getIntent().getStringExtra("location"));
        intent.putExtra("office", office.getText().toString());
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("party", getIntent().getStringExtra("party"));

        startActivity(intent);
    }

    public void onFacebook(View v) { mToast("facebook"); }

    public void onTwitter(View v) { mToast("twitter"); }

    public void onYouTube(View v) { mToast("youtube"); }

}
