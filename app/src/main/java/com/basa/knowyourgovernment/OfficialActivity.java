package com.basa.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
        loadImage(getIntent().getStringExtra("photoURL"));
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

    private void loadImage(final String url) {
        Picasso.get()
               .load(url)
               .error(R.drawable.brokenimage)
               .placeholder(R.drawable.placeholder)
               .into(avatar);
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
        if (Objects.equals(s, "Democratic Party") || Objects.equals(s, "Democratic")) {
            constraintLayout.setBackgroundColor(Color.BLUE);
            logo.setImageResource(R.drawable.dem_logo);
        }
        else if (Objects.equals(s, "Republican Party") || Objects.equals(s, "Republican")) {
            constraintLayout.setBackgroundColor(Color.RED);
            logo.setImageResource(R.drawable.rep_logo);
        }
        else { constraintLayout.setBackgroundColor(Color.BLACK); }
    }

    public void mToast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

    public void onAvatar(View v) {
        if (getIntent().getStringExtra("photoURL") == null) { mToast("No picture available"); }
        else {
            Intent intent = new Intent(this, PhotoActivity.class);

            intent.putExtra("location", getIntent().getStringExtra("location"));
            intent.putExtra("office", office.getText().toString());
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("photoURL", getIntent().getStringExtra("photoURL"));
            intent.putExtra("party", getIntent().getStringExtra("party"));

            startActivity(intent);
        }
    }

    public void onFacebook(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + getIntent().getStringExtra("facebook");
        String urlToUse;

        PackageManager packageManager = getPackageManager();

        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL; }
            else { urlToUse = "fb://page/" + getIntent().getStringExtra("facebook"); }
        }
        catch (PackageManager.NameNotFoundException e) { urlToUse = FACEBOOK_URL; }

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void onTwitter(View v) {
        Intent intent = null;
        String handle = getIntent().getStringExtra("twitter");

        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        catch (Exception e) { intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name)); }
        startActivity(intent);
    }


    public void onYouTube(View v) {
        String name = getIntent().getStringExtra("youtube");
        Intent intent = null;

        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name))); }

    }

}
