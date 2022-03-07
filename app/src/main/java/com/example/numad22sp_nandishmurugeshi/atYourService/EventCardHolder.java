package com.example.numad22sp_nandishmurugeshi.atYourService;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22sp_nandishmurugeshi.R;

import java.util.Date;

public class EventCardHolder extends RecyclerView.ViewHolder {

    public final TextView eventName;
    public final TextView venue;
    public final TextView date;
    public final ImageView image;

    public EventCardHolder(@NonNull View itemView) {
        super(itemView);
        this.eventName = itemView.findViewById(R.id.event_name);
        this.venue = itemView.findViewById(R.id.event_venue);
        this.date = itemView.findViewById(R.id.event_date);
        this.image = itemView.findViewById(R.id.event_image);
    }
}
