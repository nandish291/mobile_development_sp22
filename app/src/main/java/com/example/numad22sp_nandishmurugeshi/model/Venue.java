package com.example.numad22sp_nandishmurugeshi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {
    public String name;
    @JsonProperty("display_location")
    public String displayLocation;
}
