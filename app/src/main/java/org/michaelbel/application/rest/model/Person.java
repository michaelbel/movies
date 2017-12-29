package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

@SuppressWarnings("all")
public class Person extends RealmObject implements Serializable {

    @SerializedName("birthday")
    public String birthday;

    @SerializedName("deathday")
    public String deathday;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @Ignore
    @SerializedName("also_known_as")
    public List<String> names;

    @SerializedName("gender")
    public int gender;

    @SerializedName("biography")
    public String bio;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("place_of_birth")
    public String birthPlace;

    @SerializedName("profile_path")
    public String profilePath;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("imdb_id")
    public String imdbId;

    @SerializedName("homepage")
    public String homepage;
}