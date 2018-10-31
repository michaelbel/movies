package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Country;
import org.michaelbel.moviemade.rest.model.v3.Genre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie extends TmdbObject implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("overview")
    public String overview;

    @SerializedName("budget")
    public int budget;

    @SerializedName("revenue")
    public int revenue;

    @SerializedName("status")
    public String status;

    @SerializedName("tagline")
    public String tagline;

    @SerializedName("homepage")
    public String homepage;

    @SerializedName("imdb_id")
    public String imdbId;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("video")
    public boolean video;

    @SerializedName("runtime")
    public int runtime;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("genres")
    public List<Genre> genres;

    @SerializedName("genre_ids")
    public ArrayList<Integer> genreIds;

    @SerializedName("production_companies")
    public List<Company> companies;

    @SerializedName("production_countries")
    public List<Country> countries;

    @SerializedName("belongs_to_collection")
    public Collection belongsToCollection;

    @SerializedName("spoken_languages")
    public List<Language> languages;

    public Movie() {}
}