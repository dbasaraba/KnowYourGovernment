package com.basa.knowyourgovernment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OfficialAdapter extends RecyclerView.Adapter<OfficialViewHolder> {

    private List<Official> officials;
    private MainActivity mainActivity;

    OfficialAdapter(ArrayList<Official> officials, MainActivity mainActivity) {
        this.officials = officials;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public OfficialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View officialView = LayoutInflater.from(parent.getContext()).inflate(R.layout.official_view, parent, false);

        officialView.setOnClickListener(mainActivity);

        return new OfficialViewHolder(officialView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialViewHolder holder, int position) {
        Official o = officials.get(position);
        String namePlusParty = o.getName() + " (" + o.getParty() + " )";

        holder.representativeOffice.setTextColor(Color.rgb(86, 39, 87));
        holder.representativeOffice.setText(o.getOffice());

        holder.representativeName.setTextColor(Color.rgb(86, 39, 87));
        holder.representativeName.setText(namePlusParty);
    }

    @Override
    public int getItemCount() { return officials.size(); }

}
