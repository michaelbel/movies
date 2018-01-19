package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Keyword implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;
}