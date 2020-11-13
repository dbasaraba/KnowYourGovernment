package com.basa.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final ArrayList<Official> officials = new ArrayList<>();
    private OfficialAdapter oAdapter = new OfficialAdapter(officials, this);
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//        oAdapter.notifyDataSetChanged();
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

    private void mToast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

}
