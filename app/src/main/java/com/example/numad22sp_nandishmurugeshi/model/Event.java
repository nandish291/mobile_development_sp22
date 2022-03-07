package com.example.numad22sp_nandishmurugeshi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    @JsonProperty("datetime_local")
    public Date datetimeLocal;
    public Venue venue;
    public ArrayList<Performer> performers;
    @JsonProperty("short_title")
    public String title;
}
