package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class Genre {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;
}