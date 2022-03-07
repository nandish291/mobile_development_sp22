package com.example.numad22sp_nandishmurugeshi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Performer {
    public String image;
    @JsonProperty("short_name")
    public String shortName;
}
