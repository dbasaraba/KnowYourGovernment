package com.basa.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Objects;

public class PhotoActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    TextView currentLocation;
    TextView office;
    TextView name;
    ImageView avatar;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        setUp();
        setData();
        loadImage(getIntent().getStringExtra("photoURL"));
        setColorAndLogo(getIntent().getStringExtra("party"));
    }

    private void setUp() {
        constraintLayout = findViewById(R.id.photoConstraint);
        currentLocation = findViewById(R.id.currentLocation);
        office = findViewById(R.id.photoOffice);
        name = findViewById(R.id.photoName);
        avatar = findViewById(R.id.photoAvatar);
        logo = findViewById(R.id.photoLogo);
    }

    private void loadImage(final String url) {
        Picasso.get()
                .load(url)
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(avatar);
    }

    private void setData() {
        currentLocation.setText(getIntent().getStringExtra("location"));
        office.setText(getIntent().getStringExtra("office"));
        name.setText(getIntent().getStringExtra("name"));
    }

    private void setColorAndLogo(String s) {
        if (Objects.equals(s, "Democratic Party")) {
            constraintLayout.setBackgroundColor(Color.BLUE);
            logo.setImageResource(R.drawable.dem_logo);
        }
        else if (Objects.equals(s, "Republican Party")) {
            constraintLayout.setBackgroundColor(Color.RED);
            logo.setImageResource(R.drawable.rep_logo);
        }
        else { constraintLayout.setBackgroundColor(Color.BLACK); }
    }

}
