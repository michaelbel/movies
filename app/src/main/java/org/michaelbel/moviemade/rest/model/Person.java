package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.tmdb.TmdbObject;

import java.io.Serializable;
import java.util.List;

import io.realm.annotations.Ignore;

public class Person extends TmdbObject implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("gender")
    public int gender;

    @SerializedName("birthday")
    public String birthday;

    @SerializedName("deathday")
    public String deathday;

    @SerializedName("place_of_birth")
    public String birthPlace;

    @SerializedName("profile_path")
    public String profilePath;

    @SerializedName("biography")
    public String bio;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("imdb_id")
    public String imdbId;

    @SerializedName("homepage")
    public String homepage;

    @Ignore
    @SerializedName("also_known_as")
    public List<String> names;
}