package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class Crew {

    @SerializedName("credit_id")
    public String creditId;

    @SerializedName("department")
    public String department;

    @SerializedName("id")
    public int id;

    @SerializedName("job")
    public String job;

    @SerializedName("name")
    public String name;

    @SerializedName("profile_path")
    public String profilePath;
}
