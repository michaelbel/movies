package org.michaelbel.moviemade.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.MovieActivity;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.presenter.MoviePresenter;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Genre;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.Trailer;
import org.michaelbel.moviemade.ui.interfaces.ImageSectionListener;
import org.michaelbel.moviemade.ui.view.MovieViewLayout;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;
import org.michaelbel.moviemade.util.DateUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieFragment extends MvpAppCompatFragment implements MvpMovieView, MovieViewLayout.MovieViewListener, ImageSectionListener {

    //private final int PERMISSION_REQUEST_CODE = 100;

    private Movie extraMovie;
    private Movie loadedMovie;
    private MovieRealm extraMovieRealm;
    private MovieActivity activity;

    private ArrayList<Genre> genres = new ArrayList<>();
    private ArrayList<Trailer> trailers = new ArrayList<>();

    private ScrollView scrollView;
    private MovieViewLayout movieView;
    private SwipeRefreshLayout fragmentView;

    @InjectPresenter
    public MoviePresenter presenter;

    public static MovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static MovieFragment newInstance(MovieRealm movie) {
        Bundle args = new Bundle();
        args.putParcelable("movieRealm", movie);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MovieActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity.binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (AndroidUtils.scrollToTop()) {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (loadedMovie == null) {
                presenter.loadMovie(extraMovie);
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        scrollView = new ScrollView(activity);
        scrollView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        scrollView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        scrollView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(scrollView);

        movieView = new MovieViewLayout(activity);
        movieView.addMovieViewListener(this);
        movieView.getImagesView().addImageSectionListener(this);
        movieView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        /*movieView.addMovieViewListener(new MovieInfoLayout.MovieViewListener() {
            @Override
            public void onFavoriteButtonClick(View view) {
                boolean state = presenter.isMovieFavorite(extraMovie.id);
                presenter.setMovieFavorite(extraMovie.id, !state);

                if (view instanceof CheckedButton) {
                    ((CheckedButton) view).setChecked(!state);
                }
            }

            @Override
            public void onWatchingButtonClick(View view) {
                boolean state = presenter.isMovieWatching(extraMovie.id);
                presenter.setMovieWatching(extraMovie.id, !state);

                if (view instanceof CheckedButton) {
                    ((CheckedButton) view).setChecked(!state);
                }
            }

            @Override
            public void onGenresSectionClick(View view) {
                if (loadedMovie != null) {
                    if (loadedMovie.genres != null) {
                        if (!loadedMovie.genres.isEmpty()) {
                            genres.addAll(loadedMovie.genres);
                            activity.startGenres(genres);
                        }
                    }
                }
            }
        });*/
        scrollView.addView(movieView);
        return fragmentView;
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            //createDir();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        extraMovie = (Movie) getArguments().getSerializable("movie");
        extraMovieRealm = getArguments().getParcelable("movieRealm");

        if (extraMovie != null) {
            presenter.loadMovie(extraMovie);
        } else {
            if (NetworkUtils.notConnected()) {
                presenter.loadMovieFromRealm(extraMovieRealm.id);
            } else {
                presenter.loadMovie(extraMovieRealm); // todo ???
            }
        }
    }

    @Override
    public void showMovie(Movie movie, boolean loaded) {
        if (loaded) {
            loadedMovie = movie;
        }

        movieView.addPoster(movie.posterPath);
        movieView.addTitle(movie.title);
        movieView.addOverview(movie.overview);
        movieView.addReleaseDate(DateUtils.getMovieReleaseDate(movie.releaseDate));
        movieView.addVoteAverage(movie.voteAverage);
        movieView.addVoteCount(movie.voteCount);
        movieView.addOriginalTitle(movie.originalTitle);
        movieView.addOriginalLanguage(AndroidUtils.formatOriginalLanguage(movie.originalLanguage));
        movieView.addImages(movie.posterPath, movie.backdropPath, 0, 0);

        movieView.addTagline(movie.tagline);
        movieView.addRuntime(movie.runtime);
        movieView.addStatus(movie.status);
        movieView.addBudget(movie.budget);
        movieView.addRevenue(movie.revenue);
        movieView.addImdbpage(movie.imdbId);
        movieView.addHomepage(movie.homepage);
        movieView.addCompanies(AndroidUtils.formatCompanies(movie.companies));
        movieView.addCountries(AndroidUtils.formatCountries(movie.countries));
        movieView.setGenres(movie.genres);
        movieView.addBelongCollection(movie.belongsToCollection);

        movieView.favoriteButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.watchingButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);

        movieView.setFavoriteButton(presenter.isMovieFavorite(movie.id));
        movieView.setWatchingButton(presenter.isMovieWatching(movie.id));

        presenter.loadTrailers(movie.id);
        presenter.loadImages(movie.id);
        presenter.loadCredits(movie.id);

        genres.clear();
        genres.addAll(movie.genres);
        movieView.getGenresView().setClickable(true);
    }

    @Override
    public void showMovieRealm(MovieRealm movie) {
        movieView.addPoster(movie.posterPath);
        movieView.addTitle(movie.title);
        movieView.addOverview(movie.overview);
        movieView.addReleaseDate(movie.releaseDate);
        movieView.addVoteAverage(movie.voteAverage);
        movieView.addVoteCount(movie.voteCount);
        movieView.addOriginalTitle(movie.originalTitle);
        movieView.addOriginalLanguage(movie.originalLanguage);
        // todo Add postersCount and BackdropCount to movierealm
        movieView.addImages(movie.posterPath, movie.backdropPath, 0, 0);

        movieView.addTagline(movie.tagline);
        movieView.addRuntime(movie.runtime);
        movieView.addStatus(movie.status);
        movieView.addBudget(movie.budget);
        movieView.addRevenue(movie.revenue);
        movieView.addImdbpage(movie.imdbId);
        movieView.addHomepage(movie.homepage);

        movieView.addCompanies(null);
        movieView.addCountries(null);
        movieView.setGenres(null);
        //movieView.addBelongCollection();

        movieView.favoriteButtonVisibility(View.VISIBLE);
        movieView.watchingButtonVisibility(View.VISIBLE);

        movieView.setFavoriteButton(movie.favorite);
        movieView.setWatchingButton(movie.watching);

        movieView.addImages(null, null, 0, 0);
        movieView.addTrailers(null);
        movieView.setCrew(null);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showTrailers(List<Trailer> newTrailers) {
        trailers.clear();
        trailers.addAll(newTrailers);

        movieView.addTrailers(trailers);
        movieView.getTrailersView().setClickable(true);
    }

    @Override
    public void showImages(String posterPath, String backdropPath, int postersCount, int backdropsCount) {
        movieView.addImages(posterPath, backdropPath, postersCount, backdropsCount);
    }

    @Override
    public void showCrew(List<Crew> crews) {
        movieView.setCrew(crews);
    }

    @Override
    public void realmAdded() {

    }

    @Override
    public void onFavoriteButtonClick(View view) {
        if (loadedMovie != null) {
            presenter.setMovieFavorite(loadedMovie);
        } else if (extraMovieRealm != null) {
            presenter.setMovieFavorite(extraMovieRealm);
        }
    }

    @Override
    public void favoriteButtonState(boolean state) {
        movieView.setFavoriteButton(state);
    }

    @Override
    public void onWatchingButtonClick(View view) {
        if (loadedMovie != null) {
            presenter.setMovieWatching(loadedMovie);
        } else if (extraMovieRealm != null) {
            presenter.setMovieWatching(extraMovieRealm);
        }
    }

    @Override
    public void watchingButtonState(boolean state) {
        movieView.setWatchingButton(state);
    }

    @Override
    public void onOverviewLongClick(View view) {
        if (extraMovieRealm != null) {
            AndroidUtils.copyToClipboard(extraMovieRealm.overview);
        } else {
            AndroidUtils.copyToClipboard(extraMovie.overview);
        }

        Toast.makeText(getContext(), getString(R.string.ClipboardCopied, getString(R.string.Overview)), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrailerClick(View view, String trailerKey) {
        // todo Open in browser
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey)));
    }

    @Override
    public void onTrailersSectionClick(View view) {
        activity.startTrailers(extraMovie, trailers);
    }

    @Override
    public void onGenresSectionClick(View view) {
        activity.startGenres(genres);
    }

    @Override
    public void onMovieUrlClick(View view, int position) {
        if (extraMovieRealm != null) {
            if (position == 1) {
                Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE, extraMovieRealm.id));
            } else if (position == 2) {
                Browser.openUrl(activity, String.format(Locale.US, Url.IMDB_MOVIE, extraMovieRealm.imdbId));
            } else if (position == 3) {
                Browser.openUrl(activity, extraMovieRealm.homepage);
            }
        } else if (position == 1) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE, extraMovie.id));
        } else if (position == 2) {
            Browser.openUrl(activity, String.format(Locale.US, Url.IMDB_MOVIE, loadedMovie.imdbId));
        } else if (position == 3) {
            Browser.openUrl(activity, loadedMovie.homepage);
        }
    }

    @Override
    public void onGenreSelected(View view, Genre genre) {
        activity.startGenre(genre);
    }

    @Override
    public void onPostersClick(View view) {
        Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovie.id));
    }

    @Override
    public void onBackdropClick(View view) {
        Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovie.id));
    }
}