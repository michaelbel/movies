package org.michaelbel.moviemade.realm;

import org.michaelbel.moviemade.data.dao.Movie;

import io.realm.Realm;

@SuppressWarnings("all")
public class RealmDb {

    public static void insertOrUpdateMovie(Movie movie) {
        /*Realm realmDb = Realm.getDefaultInstance();
        realmDb.executeTransaction(realm -> {
            MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", movie.id).findFirst();
            if (movieRealm == null) {
                movieRealm = realm.createObject(MovieRealm.class);
            }

            movieRealm.adult = movie.adult;
            movieRealm.backdropPath = movie.backdropPath;
            movieRealm.budget = movie.budget;
            movieRealm.homepage = movie.homepage;
            movieRealm.id = movie.id;
            movieRealm.imdbId = movie.imdbId;
            movieRealm.originalLanguage = movie.originalLanguage;
            movieRealm.originalTitle = movie.originalTitle;
            movieRealm.overview = movie.overview;
            movieRealm.popularity = movie.popularity;
            movieRealm.posterPath = movie.posterPath;
            movieRealm.releaseDate = movie.releaseDate;
            movieRealm.revenue = movie.revenue;
            movieRealm.runtime = movie.runtime;
            movieRealm.status = movie.status;
            movieRealm.tagline = movie.tagline;
            movieRealm.title = movie.title;
            movieRealm.video = movie.video;
            movieRealm.voteAverage = movie.voteAverage;
            movieRealm.voteCount = movie.voteCount;

            realm.insertOrUpdate(movieRealm);
        });
        realmDb.close();*/
    }

    public static boolean isMovieExist(int id) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", id).findFirst();
        return movie != null;
    }

    public static void removeMovie(int id) {
        Realm realmDb = Realm.getDefaultInstance();
        realmDb.executeTransaction(realm -> {
            MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", id).findFirst();
            if (movie != null) {
                movie.deleteFromRealm();
            }
        });
        realmDb.close();
    }
}