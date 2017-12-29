package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

@SuppressWarnings("all")
public class People extends RealmObject implements Serializable {

    @SerializedName("profile_path")
    public String profilePath;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("id")
    public int id;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("name")
    public String name;

    @Ignore
    @SerializedName("known_for")
    public List<Movie> movieList;
}