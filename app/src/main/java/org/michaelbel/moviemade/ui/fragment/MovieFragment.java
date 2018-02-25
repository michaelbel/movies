package org.michaelbel.moviemade.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.app.extensions.AndroidExtensions;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.presenter.MoviePresenter;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Backdrop;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.model.v3.Poster;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.ui.MovieActivity;
import org.michaelbel.moviemade.ui.interfaces.MovieViewListener;
import org.michaelbel.moviemade.ui.view.MovieViewLayout;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;
import org.michaelbel.moviemade.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieFragment extends MvpAppCompatFragment implements MvpMovieView, MovieViewListener {

    //private final int PERMISSION_REQUEST_CODE = 100;

    private Movie extraMovie;
    private Movie loadedMovie;
    private MovieRealm extraMovieRealm;
    private MovieActivity activity;

    private ArrayList<Genre> genres = new ArrayList<>();
    private ArrayList<Trailer> trailers = new ArrayList<>();
    private ArrayList<Keyword> keywords = new ArrayList<>();

    private ScrollView scrollView;
    private MovieViewLayout movieView;
    private SwipeRefreshLayout fragmentView;
    private ViewsTransitionAnimator imageAnimator;

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
        activity.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {}

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

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
        scrollView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        scrollView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        scrollView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(scrollView);

        movieView = new MovieViewLayout(activity);
        movieView.addMovieViewListener(this);
        movieView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        scrollView.addView(movieView);

        imageAnimator = GestureTransitions.from(movieView.getPoster()).into(activity.posterFull);
        imageAnimator.addPositionUpdateListener((position, isLeaving) -> {
            activity.fullBackground.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
            activity.fullBackground.setAlpha(position);

            activity.imageToolbar.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
            activity.imageToolbar.setAlpha(position);

            activity.posterFull.setVisibility(position == 0f && isLeaving ? View.INVISIBLE : View.VISIBLE);
        });

        activity.imageToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.imageToolbar.setNavigationOnClickListener(view -> exitImage());

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

        if (savedInstanceState == null) {
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
    }

    @Override
    public void showMovie(Movie movie, boolean loaded) {
        if (loaded) {
            loadedMovie = movie;
        }

        movieView.addPoster(movie.posterPath); // from extra
        movieView.addVoteAverage(movie.voteAverage); // from extra
        movieView.addVoteCount(movie.voteCount); // from extra
        movieView.addReleaseDate(AndroidExtensions.formatReleaseDate(movie.releaseDate)); // from extra
        movieView.addRuntime(movie.runtime);
        movieView.addOriginalLanguage(AndroidUtils.formatOriginalLanguage(movie.originalLanguage)); // from extra
        movieView.addTitle(movie.title); // from extra
        movieView.addTagline(movie.tagline);

        movieView.favoriteButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.watchingButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.setFavoriteButton(presenter.isMovieFavorite(movie.id));
        movieView.setWatchingButton(presenter.isMovieWatching(movie.id));
        movieView.topLayoutLoaded();

        movieView.addOverview(movie.overview); // from extra

        movieView.addOriginalTitle(movie.originalTitle); // from extra
        movieView.addStatus(movie.status);
        movieView.addBudget(movie.budget);
        movieView.addRevenue(movie.revenue);
        movieView.addCountries(AndroidUtils.formatCountries(movie.countries));
        if (movieView.getCompanies().isEmpty()) {
            movieView.addCompanies(movie.companies);
        }

        movieView.addGenres(movie.genres);

        movieView.addImdbpage(movie.imdbId);
        movieView.addHomepage(movie.homepage);
        movieView.addCollection(movie.belongsToCollection);

        presenter.loadCredits(movie.id);
        presenter.loadTrailers(movie.id);
        presenter.loadImages(movie.id);
        presenter.loadKeywords(movie.id);

        genres.clear();
        genres.addAll(movie.genres);
        movieView.getGenresView().setClickable(true);

        fragmentView.setRefreshing(false);
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
        movieView.addImages(null, null, 0, 0);

        movieView.addTagline(movie.tagline);
        movieView.addRuntime(movie.runtime);
        movieView.addStatus(movie.status);
        movieView.addBudget(movie.budget);
        movieView.addRevenue(movie.revenue);
        movieView.addImdbpage(movie.imdbId);
        movieView.addHomepage(movie.homepage);

        movieView.addCompanies(null);
        movieView.addCountries(null);
        movieView.addGenres(null);
        //movieView.addCollection();

        movieView.favoriteButtonVisibility(View.VISIBLE);
        movieView.watchingButtonVisibility(View.VISIBLE);

        movieView.setFavoriteButton(movie.favorite);
        movieView.setWatchingButton(movie.watching);

        movieView.addTrailers(null);
        movieView.setCrew(null);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showTrailers(List<Trailer> results) {
        trailers.clear();
        trailers.addAll(results);

        movieView.addTrailers(trailers);
        movieView.getTrailersView().setClickable(true);
    }

    @Override
    public void showImages(List<Poster> posters, List<Backdrop> backdrops, int postersCount, int backdropsCount) {
        movieView.addImages(posters, backdrops, postersCount, backdropsCount);
        /*movieView.getImagesView().getPostersAdapter().getView(movieView.getImagesView().getPosterViewPager().getCurrentItem()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("2580", "Posters Click");
                if (extraMovie != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovie.id));
                } else if (extraMovieRealm != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovieRealm.id));
                }
            }
        });
        movieView.getImagesView().getBackdropsAdapter().getView(movieView.getImagesView().getBackdropViewPager().getCurrentItem()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("2580", "Backdrops Click");
                if (extraMovie != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovie.id));
                } else if (extraMovieRealm != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovieRealm.id));
                }
            }
        });*/
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
    public void onTrailerClick(View view, String trailerKey) {
        // todo Open in browser
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey)));
    }

    @Override
    public void onTrailersSectionClick(View view) {
        activity.startTrailers(loadedMovie.title, trailers);
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
    public void onGenreClick(View view, Genre genre) {
        activity.startGenre(genre);
    }

    @Override
    public void onPostersClick(View view) {
        Log.e("2580", "Posters Click");
        if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovieRealm.id));
        }
    }

    @Override
    public void onBackdropsClick(View view) {
        Log.e("2580", "Backdrops Click");
        if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovieRealm.id));
        }
    }

    @Override
    public void onKeywordClick(View view, Keyword keyword) {
        activity.startKeyword(keyword);
    }

    @Override
    public void showKeywords(List<Keyword> results) {
        keywords.clear();
        keywords.addAll(results);

        if (movieView.getKeywords().isEmpty()) {
            movieView.addKeywords(keywords);
        }
    }

    @Override
    public void onCollectionClick(View view) {
        activity.startCollection(loadedMovie.belongsToCollection);
    }

    @Override
    public void onCompanyClick(View view, Company company) {
        activity.startCompany(company);
    }

    @Override
    public void onPosterClick(View view) {
        activity.getSettingsController().apply(activity.posterFull);
        imageAnimator.enterSingle(true);
    }

    public boolean isOpenImage() {
        return !imageAnimator.isLeaving();
    }

    public void exitImage() {
        if (!imageAnimator.isLeaving()) {
            imageAnimator.exit(true);
        }
    }
}