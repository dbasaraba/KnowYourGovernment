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

import org.w3c.dom.Text;

import java.util.Objects;

public class PhotoActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
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
        setColorAndLogo(getIntent().getStringExtra("party"));
    }

    private void setUp() {
        constraintLayout = findViewById(R.id.photoConstraint);
        office = findViewById(R.id.photoOffice);
        name = findViewById(R.id.photoName);
        avatar = findViewById(R.id.photoAvatar);
        logo = findViewById(R.id.photoLogo);
    }

    private void setData() {
        office.setText(getIntent().getStringExtra("office"));
        name.setText(getIntent().getStringExtra("name"));
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

}
