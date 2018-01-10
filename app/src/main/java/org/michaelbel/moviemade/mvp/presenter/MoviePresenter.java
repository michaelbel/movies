package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.Trailer;
import org.michaelbel.moviemade.rest.response.CreditResponse;
import org.michaelbel.moviemade.rest.response.ImageResponse;
import org.michaelbel.moviemade.rest.response.TrailersResponse;
import org.michaelbel.moviemade.app.ApiFactory;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class MoviePresenter extends MvpPresenter<MvpMovieView> {

    public void loadMovie(Movie movie) {
        if (NetworkUtils.notConnected()) {
            //getViewState().showMovieFromExtra(movie);
            getViewState().showMovie(movie, false);
        } else {
            loadMovieDetails(movie.id);
        }
    }

    public void loadMovieDetails(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<Movie> call = service.getDetails(movieId, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    getViewState().showMovie(movie, true);
                } else {
                    getViewState().showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                getViewState().showError();
            }
        });
    }

    public void loadTrailers(int movieId) {
        List<Trailer> trailers = new ArrayList<>();

        if (NetworkUtils.notConnected()) {
            getViewState().showTrailers(trailers);
        }

        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<TrailersResponse> call = service.getVideos(movieId, Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailersResponse> call, @NonNull Response<TrailersResponse> response) {
                if (response.isSuccessful()) {
                    getViewState().showTrailers(response.body().trailers);
                } else {
                    getViewState().showTrailers(trailers);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailersResponse> call, @NonNull Throwable t) {
                getViewState().showTrailers(trailers);
            }
        });
    }

    public void loadImages(int movieId) {
        if (NetworkUtils.notConnected()) {
            return;
        }

        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<ImageResponse> call = service.getImages(movieId, Url.TMDB_API_KEY, null);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImageResponse> call, @NonNull Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    getViewState().showImages(response.body().posters.size(), response.body().backdrops.size());
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void loadCredits(int movieId) {
        List<Crew> crews = new ArrayList<>();

        if (NetworkUtils.notConnected()) {
            getViewState().showCrew(crews);
        }

        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<CreditResponse> call = service.getCredits(movieId, Url.TMDB_API_KEY);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreditResponse> call, @NonNull Response<CreditResponse> response) {
                if (response.isSuccessful()) {
                    getViewState().showCrew(response.body().crews);
                } else {
                    getViewState().showCrew(crews);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable t) {
                getViewState().showCrew(crews);
            }
        });
    }




















    /*public boolean isMovieRealm(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        //MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirstAsync();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        if (movie != null) {
            return true;
        } else {
            return false;
        }
    }

    public void addMovieToRealm(Movie movie) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", movie.id).findFirstAsync();
        if (movieRealm == null) {
            realm.beginTransaction();
                MovieRealm newMovie = realm.createObject(MovieRealm.class);
                newMovie.id = movie.id;
                newMovie.title = movie.title;
                newMovie.posterPath = movie.posterPath;
                newMovie.releaseDate = DateUtils.getMovieReleaseDate(movie.releaseDate);
                newMovie.originalTitle = movie.originalTitle;
                newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(movie.originalLanguage);
                newMovie.overview = movie.overview;
                newMovie.favorite = isMovieFavorite(movie.id);
                newMovie.watching = isMovieWatching(movie.id);
                newMovie.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
                newMovie.adult = movie.adult;
                newMovie.backdropPath = movie.backdropPath;
                newMovie.budget = AndroidUtils.formatCurrency(movie.budget);
                newMovie.revenue = AndroidUtils.formatCurrency(movie.revenue);
                newMovie.status = movie.status;
                newMovie.tagline = movie.tagline;
                newMovie.imdbId = movie.imdbId;
                newMovie.homepage = movie.homepage;
                newMovie.popularity = movie.popularity;
                newMovie.video = movie.video;
                newMovie.runtime = AndroidUtils.formatRuntime(movie.runtime);
                newMovie.voteAverage = movie.voteAverage;
                newMovie.voteCount = movie.voteCount;
                newMovie.countries = AndroidUtils.formatCountries(movie.countries);
                newMovie.companies = AndroidUtils.formatCompanies(movie.companies);
                newMovie.genres = AndroidUtils.formatGenres(movie.genres);
            realm.commitTransaction();

            getViewState().showMovie(newMovie);
        }
    }

    public void updateMovieToRealm(Movie movie) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", movie.id).findFirstAsync();
        realm.beginTransaction();
            movieRealm.id = movie.id;
            movieRealm.title = movie.title;
            movieRealm.posterPath = movie.posterPath;
            movieRealm.releaseDate = DateUtils.getMovieReleaseDate(movie.releaseDate);
            movieRealm.originalTitle = movie.originalTitle;
            movieRealm.originalLanguage = AndroidUtils.formatOriginalLanguage(movie.originalLanguage);
            movieRealm.overview = movie.overview;
            movieRealm.favorite = isMovieFavorite(movie.id);
            movieRealm.watching = isMovieWatching(movie.id);
            movieRealm.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
            movieRealm.adult = movie.adult;
            movieRealm.backdropPath = movie.backdropPath;
            movieRealm.budget = AndroidUtils.formatCurrency(movie.budget);
            movieRealm.revenue = AndroidUtils.formatCurrency(movie.revenue);
            movieRealm.status = movie.status;
            movieRealm.tagline = movie.tagline;
            movieRealm.imdbId = movie.imdbId;
            movieRealm.homepage = movie.homepage;
            movieRealm.popularity = movie.popularity;
            movieRealm.video = movie.video;
            movieRealm.runtime = AndroidUtils.formatRuntime(movie.runtime);
            movieRealm.voteAverage = movie.voteAverage;
            movieRealm.voteCount = movie.voteCount;
            movieRealm.countries = AndroidUtils.formatCountries(movie.countries);
            movieRealm.companies = AndroidUtils.formatCompanies(movie.companies);
            movieRealm.genres = AndroidUtils.formatGenres(movie.genres);
        realm.commitTransaction();
    }

    public void loadMovieFromRealm(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        //MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirstAsync();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        if (movie != null) {
            getViewState().showMovie(movie);
        } else {
            getViewState().showError();
        }
    }

    *//*public void loadMovieDetails(int movieId, boolean needUpdate) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<Movie> call = service.getDetails(movieId, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    *//**//*if (needUpdate) {
                        updateMovieToRealm(response.body());
                    } else {
                        addMovieToRealm(response.body());
                    }*//**//*
                    getViewState().showMovie(response.body());
                    loadTrailers(movieId);
                    loadImages(movieId);
                    loadCredits(movieId);
                } else {
                    getViewState().showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                getViewState().showError();
            }
        });
    }*//*

    public void addMovieExtrasToRealm(Movie movie) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            MovieRealm newMovie = realm1.createObject(MovieRealm.class);
            newMovie.id = movie.id;
            newMovie.title = movie.title;
            newMovie.posterPath = movie.posterPath;
            newMovie.adult = movie.adult;
            newMovie.overview = movie.overview;
            newMovie.releaseDate = DateUtils.getMovieReleaseDate(movie.releaseDate);
            newMovie.originalTitle = movie.originalTitle;
            newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(movie.originalLanguage);
            newMovie.backdropPath = movie.backdropPath;
            newMovie.popularity = movie.popularity;
            newMovie.video = movie.video;
            newMovie.voteAverage = movie.voteAverage;
            newMovie.voteCount = movie.voteCount;
            newMovie.favorite = false;
            newMovie.watching = false;
        });

        getViewState().movieFromExtrasLoaded();
    }

    public boolean isMovieFavorite(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirstAsync();
        return movie != null && movie.favorite;
    }

    public boolean isMovieWatching(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirstAsync();
        return movie != null && movie.watching;
    }

    public void setMovieFavorite(int movieId, boolean favorite) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirstAsync();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                movie.favorite = favorite;
            }
        });
    }

    public void setMovieWatching(int movieId, boolean watching) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirstAsync();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                movie.watching = watching;
            }
        });
    }*/

    /*private void cashMovie() {
        Picasso.with(getContext())
               .load(Url.getImage(extraMovie.posterPath, "original"))
               .into(target);
    }*/

    /*private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(() -> {
                String imageName = "/poster_" + extraMovie.id + ".jpg";

                File file = new File(AndroidUtils.getCacheDirectory() + imageName);
                try {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {}

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {}
    };*/
}