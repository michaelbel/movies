package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.TmdbObject;

import java.io.Serializable;
import java.util.List;

public class People extends TmdbObject implements Serializable {

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

    @SerializedName("known_for")
    public List<Movie> movies;
}