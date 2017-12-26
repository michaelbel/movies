package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class Cast {

    @SerializedName("cast_id")
    public int castId;

    @SerializedName("character")
    public String character;

    @SerializedName("credit_id")
    public String creditId;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("order")
    public int order;

    @SerializedName("profile_path")
    public String profilePath;
}