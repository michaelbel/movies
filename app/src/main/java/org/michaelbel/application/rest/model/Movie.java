package org.michaelbel.application.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

@SuppressWarnings("all")
public class Movie extends RealmObject implements Serializable {

    public boolean favorite;
    public boolean watching;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("budget")
    public int budget;

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

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("revenue")
    public int revenue;

    @SerializedName("runtime")
    public int runtime;

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

    @Ignore
    @SerializedName("genres")
    public List<Genre> genresList;

    @Ignore
    @SerializedName("production_companies")
    public List<Companies> companiesList;

    @Ignore
    @SerializedName("production_countries")
    public List<Countries> countriesList;

    @Ignore
    @SerializedName("spoken_languages")
    public List<Languages> languagesList;

    @Ignore
    @SerializedName("belongs_to_collection")
    public BelongsToCollection belongsToCollection;

    public class Companies implements Serializable {

        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;
    }

    public class Countries implements Serializable {

        @SerializedName("iso_3166_1")
        public String country;

        @SerializedName("name")
        public String name;
    }

    public class Languages implements Serializable {

        @SerializedName("iso_639_1")
        public String language;

        @SerializedName("name")
        public String name;
    }

    public class BelongsToCollection implements Serializable {

        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

        @SerializedName("poster_path")
        public String posterPath;

        @SerializedName("backdrop_path")
        public String backdropPath;
    }

    public Movie() {}
}