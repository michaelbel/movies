package org.michaelbel.moviemade.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

@SuppressWarnings("all")
public class Movie extends RealmObject implements Serializable {

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

    @Ignore
    @SerializedName("genres")
    public List<Genre> genres;

    @Ignore
    @SerializedName("production_companies")
    public List<Company> companies;

    @Ignore
    @SerializedName("production_countries")
    public List<Country> countries;

    @Ignore
    @SerializedName("belongs_to_collection")
    public BelongsToCollection belongsToCollection;

    public Movie() {}






    public boolean favorite;
    public boolean watching;
    public String addedDate;

    @Ignore
    @SerializedName("spoken_languages")
    public List<Language> languages;
}