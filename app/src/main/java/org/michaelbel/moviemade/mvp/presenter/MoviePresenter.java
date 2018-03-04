package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.material.annotation.Beta;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.extensions.AndroidExtensions;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.rest.response.CreditResponse;
import org.michaelbel.moviemade.rest.response.ImageResponse;
import org.michaelbel.moviemade.rest.response.KeywordResponse;
import org.michaelbel.moviemade.rest.response.TrailersResponse;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.DateUtils;
import org.michaelbel.moviemade.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

@InjectViewState
public class MoviePresenter extends MvpPresenter<MvpMovieView> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadMovie(Movie movie) {
        if (NetworkUtils.notConnected()) {
            if (isMovieRealm(movie.id)) {
                loadMovieFromRealm(movie.id);
            } else {
                getViewState().showMovie(movie, false);
            }
        } else {
            loadMovieDetails(movie.id);
        }
    }

    public void loadMovie(MovieRealm movie) {
        loadMovieDetails(movie.id);
    }

    private void loadMovieDetails(int movieId) {
        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<Movie> observable = service.getDetails(movieId, Url.TMDB_API_KEY, Url.en_US, null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<Movie>() {
            @Override
            public void onNext(Movie movie) {
                getViewState().showMovie(movie, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError();
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadTrailers(int movieId) {
        List<Trailer> trailers = new ArrayList<>();

        if (NetworkUtils.notConnected()) {
            getViewState().showTrailers(trailers);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<TrailersResponse> observable = service.getVideos(movieId, Url.TMDB_API_KEY, Url.en_US).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<TrailersResponse>() {
            @Override
            public void onNext(TrailersResponse response) {
                getViewState().showTrailers(response.trailers);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showTrailers(trailers);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadKeywords(int movieId) {
        List<Keyword> keywords = new ArrayList<>();

        if (NetworkUtils.notConnected()) {
            getViewState().showKeywords(keywords);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<KeywordResponse> observable = service.getKeywords(movieId, Url.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<KeywordResponse>() {
            @Override
            public void onNext(KeywordResponse response) {
                getViewState().showKeywords(response.keywords);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showKeywords(keywords);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadImages(int movieId) {
        if (NetworkUtils.notConnected()) {
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<ImageResponse> observable = service.getImages(movieId, Url.TMDB_API_KEY, null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<ImageResponse>() {
            @Override
            public void onNext(ImageResponse response) {
                getViewState().showImages(response.posters, response.backdrops, response.posters.size(), response.backdrops.size());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadCredits(int movieId) {
        List<Crew> crews = new ArrayList<>();

        if (NetworkUtils.notConnected()) {
            getViewState().showCrew(crews);
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<CreditResponse> observable = service.getCredits(movieId, Url.TMDB_API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CreditResponse>() {
            @Override
            public void onNext(CreditResponse response) {
                getViewState().showCrew(response.crews);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showCrew(crews);
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

//--------------------------------------------------------------------------------------------------

    @Beta
    private boolean isMovieRealm(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        return movie != null;
    }

    public boolean isMovieFavorite(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        // Async query
        /*realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        });*/
        //realm.close();
        return movie != null && movie.favorite;
    }

    public boolean isMovieWatching(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        return movie != null && movie.watching;
    }

    public void setMovieFavorite(Movie m) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
        if (movie == null) {
            realm.beginTransaction();

            MovieRealm newMovie = realm.createObject(MovieRealm.class);
            newMovie.id = m.id;
            newMovie.title = m.title;
            newMovie.posterPath = m.posterPath;
            newMovie.releaseDate = AndroidExtensions.formatReleaseDate(m.releaseDate);
            newMovie.originalTitle = m.originalTitle;
            newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(m.originalLanguage);
            newMovie.overview = m.overview;
            newMovie.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
            newMovie.adult = m.adult;
            newMovie.backdropPath = m.backdropPath;
            newMovie.budget = AndroidUtils.formatCurrency(m.budget);
            newMovie.revenue = AndroidUtils.formatCurrency(m.revenue);
            newMovie.status = m.status;
            newMovie.tagline = m.tagline;
            newMovie.imdbId = m.imdbId;
            newMovie.homepage = m.homepage;
            newMovie.popularity = m.popularity;
            newMovie.video = m.video;
            newMovie.runtime = AndroidExtensions.formatRuntime(m.runtime);
            newMovie.voteAverage = m.voteAverage;
            newMovie.voteCount = m.voteCount;
            newMovie.favorite = true;

            realm.commitTransaction();
            getViewState().favoriteButtonState(true);
        } else {
            boolean state = movie.favorite;
            realm.beginTransaction();
            MovieRealm movie2 = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
            movie2.favorite = !state;
            getViewState().favoriteButtonState(!state);
            realm.commitTransaction();
        }
        realm.close();
    }

    public void setMovieFavorite(MovieRealm mr) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", mr.id).findFirst();

        boolean state = movie.favorite;
        realm.beginTransaction();
        MovieRealm movie2 = realm.where(MovieRealm.class).equalTo("id", mr.id).findFirst();
        movie2.favorite = !state;
        getViewState().favoriteButtonState(!state);
        realm.commitTransaction();
        realm.close();
    }

    public void setMovieWatching(Movie m) {
        Realm realm = Realm.getDefaultInstance();

        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
        if (movie == null) {
            realm.beginTransaction();

            MovieRealm newMovie = realm.createObject(MovieRealm.class);
            newMovie.id = m.id;
            newMovie.title = m.title;
            newMovie.posterPath = m.posterPath;
            newMovie.releaseDate = AndroidExtensions.formatReleaseDate(m.releaseDate);
            newMovie.originalTitle = m.originalTitle;
            newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(m.originalLanguage);
            newMovie.overview = m.overview;
            newMovie.addedDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
            newMovie.adult = m.adult;
            newMovie.backdropPath = m.backdropPath;
            newMovie.budget = AndroidUtils.formatCurrency(m.budget);
            newMovie.revenue = AndroidUtils.formatCurrency(m.revenue);
            newMovie.status = m.status;
            newMovie.tagline = m.tagline;
            newMovie.imdbId = m.imdbId;
            newMovie.homepage = m.homepage;
            newMovie.popularity = m.popularity;
            newMovie.video = m.video;
            newMovie.runtime = AndroidExtensions.formatRuntime(m.runtime);
            newMovie.voteAverage = m.voteAverage;
            newMovie.voteCount = m.voteCount;
            newMovie.watching = true;

            realm.commitTransaction();
            getViewState().watchingButtonState(true);
        } else {
            boolean state = movie.isWatching();
            realm.beginTransaction();
            MovieRealm movie2 = realm.where(MovieRealm.class).equalTo("id", m.id).findFirst();
            movie2.watching = !state;
            getViewState().watchingButtonState(!state);
            realm.commitTransaction();
        }
        realm.close();
    }

    public void setMovieWatching(MovieRealm mr) {
        Realm realm = Realm.getDefaultInstance();

        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", mr.id).findFirst();

        boolean state = movie.watching;
        realm.beginTransaction();
        MovieRealm movie2 = realm.where(MovieRealm.class).equalTo("id", mr.id).findFirst();
        movie2.watching = !state;
        getViewState().watchingButtonState(!state);
        realm.commitTransaction();
        realm.close();
    }

    /*@Beta
    private void updateMovieToRealm(Movie movie) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm1 -> {
            MovieRealm movieRealm = realm1.where(MovieRealm.class).equalTo("id", movie.id).findFirst();
            movieRealm.id = movie.id;
            movieRealm.title = movie.title;
            movieRealm.posterPath = movie.posterPath;
            movieRealm.releaseDate = AndroidExtensions.formatReleaseDate(movie.releaseDate);
            movieRealm.originalTitle = movie.originalTitle;
            movieRealm.originalLanguage = AndroidUtils.formatOriginalLanguage(movie.originalLanguage);
            movieRealm.overview = movie.overview;
            //movieRealm.favorite = isMovieFavorite(movie.id);
            //movieRealm.watching = isMovieWatching(movie.id);
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
            movieRealm.runtime = AndroidExtensions.formatRuntime(movie.runtime);
            movieRealm.voteAverage = movie.voteAverage;
            movieRealm.voteCount = movie.voteCount;
            //movieRealm.countries = AndroidUtils.formatCountries(movie.countries);
            //movieRealm.companies = AndroidUtils.formatCompanies(movie.companies);
            //movieRealm.genres = AndroidUtils.formatGenres(movie.genres);
            realm1.commitTransaction();
        });
        realm.close();
        getViewState().realmAdded();
    }*/

    /*@Beta
    private void addMovieToRealm(Movie movie) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movieRealm = realm.where(MovieRealm.class).equalTo("id", movie.id).findFirst();

        if (movieRealm != null) {
            updateMovieToRealm(movie);
            return;
        }

        realm.executeTransaction(realm1 -> {
            MovieRealm newMovie = realm1.createObject(MovieRealm.class);
            newMovie.id = movie.id;
            newMovie.title = movie.title;
            newMovie.posterPath = movie.posterPath;
            newMovie.releaseDate = DateUtils.formatReleaseDate(movie.releaseDate);
            newMovie.originalTitle = movie.originalTitle;
            newMovie.originalLanguage = AndroidUtils.formatOriginalLanguage(movie.originalLanguage);
            newMovie.overview = movie.overview;
            //newMovie.setFavorite(false);
            //newMovie.setWatching(false);
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
            //newMovie.countries = AndroidUtils.formatCountries(movie.countries);
            //newMovie.companies = AndroidUtils.formatCompanies(movie.companies);
            //newMovie.genres = AndroidUtils.formatGenres(movie.genres);
            realm1.commitTransaction();
        });
        realm.close();
    }*/

    @Beta
    public void loadMovieFromRealm(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        realm.close();
        getViewState().showMovieRealm(movie);
    }

    /*public void loadMovieFromRealm(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        //MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirstAsync();
        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieId).findFirst();
        if (movie != null) {
            getViewState().showMovie(movie);
        } else {
            getViewState().showNoConnection();
        }
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