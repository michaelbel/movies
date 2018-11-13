package org.michaelbel.moviemade.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie {

    @PrimaryKey
    public long id;

    public boolean adult;

    public String backdropPath;

    //public Collection belongsToCollection;

    public int budget;

    //public List<Genre> genres;

    public String homepage;

    public int movieId;

    public String imdbId;

    public String originalLanguage;

    public String originalTitle;

    public String overview;

    public double popularity;

    public String posterPath;

    //public List<Company> companies;

    //public List<Country> countries;

    public String releaseDate;

    public int revenue;

    public int runtime;

    //public List<Language> languages;

    public String status;

    public String tagline;

    public String title;

    public boolean video;

    public float voteAverage;

    public int voteCount;

//--Other fields------------------------------------------------------------------------------------

    public boolean watching;

    public String addedDate;

    public String lastSeenDate;

    public int seenCountDate;
}