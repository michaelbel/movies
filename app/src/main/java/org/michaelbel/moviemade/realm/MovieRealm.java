package org.michaelbel.moviemade.realm;

import org.michaelbel.moviemade.data.dao.Collection;
import org.michaelbel.moviemade.data.dao.Company;
import org.michaelbel.moviemade.data.dao.Genre;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

@SuppressWarnings("all")
public class MovieRealm extends RealmObject {

    public boolean adult;

    public String backdropPath;

    @Ignore
    public Collection belongsToCollection;

    public int budget;

    @Ignore
    public List<Genre> genres;

    public String homepage;

    public int id;

    public String imdbId;

    public String originalLanguage;

    public String originalTitle;

    public String overview;

    public double popularity;

    public String posterPath;

    @Ignore
    public List<Company> companies;

    @Ignore
    //public List<Country> countries;

    public String releaseDate;

    public int revenue;

    public int runtime;

    @Ignore
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