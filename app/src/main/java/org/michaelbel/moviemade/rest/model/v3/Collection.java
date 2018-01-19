package org.michaelbel.moviemade.rest.model.v3;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Movie;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Collection extends RealmObject implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("overview")
    public String overview;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @Ignore
    @SerializedName("parts")
    public List<Movie> movies;
}