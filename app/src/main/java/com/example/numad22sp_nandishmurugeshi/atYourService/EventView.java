package com.example.numad22sp_nandishmurugeshi.atYourService;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22sp_nandishmurugeshi.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class EventView extends RecyclerView.Adapter<EventCardHolder> {

    private final List<EventCard> eventCards;

    public EventView(List<EventCard> eventCards) {
        this.eventCards = eventCards;
    }

    @NonNull
    @Override
    public EventCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCardHolder holder, int position) {
        EventCard eventCard = eventCards.get(position);

        holder.eventName.setText(eventCard.getEventName());
        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(eventCard.getDate());
        holder.date.setText(date);
        holder.venue.setText(eventCard.getVenue());
        final Drawable[] drawable = new Drawable[1];
        Thread retrieveImage = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream image = null;
                try {
                    image = (InputStream) new URL(eventCard.getImage()).getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                drawable[0] = Drawable.createFromStream(image, "src");
            }
        });
        retrieveImage.start();
        try {
            retrieveImage.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        holder.image.setImageDrawable(drawable[0]);
    }

    @Override
    public int getItemCount() {
        return eventCards.size();
    }
}
