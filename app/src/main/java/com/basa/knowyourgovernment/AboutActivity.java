package com.basa.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView civicAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        civicAPI = findViewById(R.id.civicAPI);
        civicAPI.setMovementMethod(LinkMovementMethod.getInstance());
        civicAPI.setLinkTextColor(Color.WHITE);
    }

}
