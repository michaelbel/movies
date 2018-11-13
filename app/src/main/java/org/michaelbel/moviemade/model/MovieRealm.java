package org.michaelbel.moviemade.model;

import io.realm.RealmObject;

@Deprecated
public class MovieRealm extends RealmObject {

    public boolean watching;

    public int id;
    public String title;
    public String originalTitle;
    public String originalLanguage;
    public boolean adult;
    public String posterPath;
    public String backdropPath;
    public String releaseDate;
    public String overview;
    public String addedDate;
    public String budget;
    public String revenue;
    public String status;
    public String tagline;
    public String imdbId;
    public String homepage;
    public double popularity;
    public boolean video;
    public String runtime;
    public float voteAverage;
    public int voteCount;
    public String genres;
    public String companies;
    public String countries;

    public MovieRealm() {}
}