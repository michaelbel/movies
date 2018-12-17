package org.michaelbel.moviemade.data.eventbus;

public class Events {

    public static class MovieListUpdateAdult {}

    public static class MovieListRefreshLayout {}

    public static class DeleteMovieFromFavorite {

        public int movieId;

        public DeleteMovieFromFavorite(int movieId) {
            this.movieId = movieId;
        }
    }
}