package com.basa.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class OfficialActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    TextView office;
    TextView name;
    TextView party;
    TextView addressLineOne;
    TextView addressLineTwo;
    TextView addressLineThree;
    TextView phone;
    TextView email;
    TextView website;
    ImageView avatar;
    ImageView partyLogo;
    ImageView facebook;
    ImageView twitter;
    ImageView youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        findAndSetValues();
        setColor(getIntent().getStringExtra("party"));
        setDummyData();
    }

    private void findAndSetValues() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.scrollableConstraint);
        office = findViewById(R.id.office);
        name = findViewById(R.id.name);
        party = findViewById(R.id.party);
        addressLineOne = findViewById(R.id.addressLineOne);
        addressLineTwo = findViewById(R.id.addressLineTwo);
        addressLineThree = findViewById(R.id.addressLineThree);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        website = findViewById(R.id.website);
        avatar = findViewById(R.id.avatar);
        partyLogo = findViewById(R.id.partyLogo);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        youtube = findViewById(R.id.youtube);
    }

    private void setDummyData() {
        office.setText(getIntent().getStringExtra("office"));
        name.setText(getIntent().getStringExtra("name"));
        String partyText = '(' + getIntent().getStringExtra("party") + ')';
        party.setText(partyText);
        addressLineOne.setText(getIntent().getStringExtra("addressLineOne"));
        addressLineTwo.setText(getIntent().getStringExtra("addressLineTwo"));
        String city = getIntent().getStringExtra("addressCity");
        String state = getIntent().getStringExtra("addressState");
        String zip = getIntent().getStringExtra("addressZip");
        String lineThree = city + ", " + state + ' ' + zip;
        addressLineThree.setText(lineThree);
        phone.setText(getIntent().getStringExtra("phone"));
        email.setText(getIntent().getStringExtra("email"));
        website.setText(getIntent().getStringExtra("website"));
    }

    private void setColor(String s) {
        if (Objects.equals(s, "Democratic")) { constraintLayout.setBackgroundColor(Color.BLUE); }
        else if (Objects.equals(s, "Republican")) { constraintLayout.setBackgroundColor(Color.RED); }
        else { constraintLayout.setBackgroundColor(Color.BLACK); }
    }

    private void mToast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

    public void onAvatar(View v) { mToast("avatar"); }

    public void onParty(View v) { mToast("partyLogo"); }

    public void onFacebook(View v) { mToast("facebook"); }

    public void onTwitter(View v) { mToast("twitter"); }

    public void onYouTube(View v) { mToast("youtube"); }

}
