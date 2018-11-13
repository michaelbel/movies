package org.michaelbel.tmdb.v3.json;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.moviemade.rest.model.Language;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Country;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.tmdb.TmdbObject;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("all")
public class Movie extends TmdbObject implements Serializable {

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("belongs_to_collection")
    public Collection belongsToCollection;

    @SerializedName("budget")
    public int budget;

    @SerializedName("genres")
    public List<Genre> genres;

    @SerializedName("homepage")
    public String homepage;

    @SerializedName("id")
    public int id;

    @SerializedName("imdb_id")
    public String imdbId;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("overview")
    public String overview;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("production_companies")
    public List<Company> companies;

    @SerializedName("production_countries")
    public List<Country> countries;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("revenue")
    public int revenue;

    @SerializedName("runtime")
    public int runtime;

    @SerializedName("spoken_languages")
    public List<Language> languages;

    @SerializedName("status")
    public String status;

    @SerializedName("tagline")
    public String tagline;

    @SerializedName("title")
    public String title;

    @SerializedName("video")
    public boolean video;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;
}