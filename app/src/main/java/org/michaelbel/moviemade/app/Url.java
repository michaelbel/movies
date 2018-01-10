package org.michaelbel.moviemade.app;

import org.michaelbel.moviemade.util.AndroidUtils;

public class Url {

    public static final String GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static final String IMDB_MOVIE = "http://imdb.com/title/%s";

    public static final String TMDB_API_KEY = AndroidUtils.loadProperty("TMDbApiKey");
    public static final String TMDB_API_TOKEN = AndroidUtils.loadProperty("TMDbApiToken");

    public static final String TMDB_API_V3 = "https://api.themoviedb.org/3/";
    public static final String TMDB_MOVIE = "https://themoviedb.org/movie/%d";
    public static final String TMDB_PERSON = "https://themoviedb.org/person/%d";
    public static final String TMDB_IMAGE = "http://image.tmdb.org/t/p/%s/%s";
    public static final String TMDB_MOVIE_POSTERS = "https://themoviedb.org/movie/%d/images/posters";
    public static final String TMDB_MOVIE_BACKDROPS = "https://themoviedb.org/movie/%d/images/backdrops";

    public static final String en_US = "en-US";
    public static final String de_DE = "de-DE";
    public static final String ru_RU = "ru-RU";
    public static final String pt_BR = "pt-BR";
    public static final String pt_PT = "pt-PT";
    public static final String pt_US = "pt-US";






}