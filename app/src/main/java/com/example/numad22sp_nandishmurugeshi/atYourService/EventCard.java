package com.example.numad22sp_nandishmurugeshi.atYourService;

import java.util.Date;

public class EventCard {

    private final String eventName;
    private final String venue;
    private final Date date;
    private final String image;

    public EventCard(String eventName, String performerName, String venue, Date date, String image) {
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.image = image;
    }

    public String getEventName() {
        return eventName;
    }

    public String getVenue() {
        return venue;
    }

    public Date getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
}
