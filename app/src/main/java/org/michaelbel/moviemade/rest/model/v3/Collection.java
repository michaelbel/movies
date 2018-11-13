package org.michaelbel.moviemade.rest.model.v3;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.TmdbObject;

import java.util.List;

public class Collection extends TmdbObject {

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

    @SerializedName("parts")
    public List<Movie> movies;

    public Collection() {}
}