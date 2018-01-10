package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

@SuppressWarnings("all")
public class Crew extends RealmObject implements Serializable {

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
