package com.basa.knowyourgovernment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfficialViewHolder extends RecyclerView.ViewHolder {

    TextView representativeOffice;
    TextView representativeName;

    public OfficialViewHolder(@NonNull View v) {
        super(v);

        representativeOffice = v.findViewById(R.id.representativeOffice);
        representativeName = v.findViewById(R.id.representativeName);
    }

}
