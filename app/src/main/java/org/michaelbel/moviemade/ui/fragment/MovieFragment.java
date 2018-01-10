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
import org.michaelbel.moviemade.mvp.presenter.MoviePresenter;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Genre;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.Trailer;
import org.michaelbel.moviemade.ui.interfaces.ImageSectionListener;
import org.michaelbel.moviemade.ui.view.MovieViewLayout;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieFragment extends MvpAppCompatFragment implements MvpMovieView, MovieViewLayout.MovieViewListener, ImageSectionListener {

    private final int PERMISSION_REQUEST_CODE = 100;

    private Movie extraMovie;
    private Movie loadedMovie;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity = (MovieActivity) getActivity();

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

        if (getArguments() != null) {
            if (getArguments().getSerializable("movie") != null) {
                extraMovie = (Movie) getArguments().getSerializable("movie");
            }
        }

        presenter.loadMovie(extraMovie);
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
        movieView.addImages(extraMovie.posterPath, extraMovie.backdropPath, 0, 0);

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

        movieView.favoriteButtonVisibility(movie.favorite ? View.VISIBLE : View.GONE);
        movieView.watchingButtonVisibility(movie.watching ? View.VISIBLE : View.GONE);

        presenter.loadTrailers(movie.id);
        presenter.loadImages(movie.id);
        presenter.loadCredits(movie.id);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showTrailers(List<Trailer> trailers) {
        movieView.addTrailers(trailers);
        movieView.getTrailersView().setClickable(true);
        this.trailers.addAll(trailers);
    }

    @Override
    public void showImages(int postersCount, int backdropsCount) {
        movieView.addImages(extraMovie.posterPath, extraMovie.backdropPath, postersCount, backdropsCount);
    }

    @Override
    public void showCrew(List<Crew> crews) {
        movieView.setCrew(crews);
    }

    @Override
    public boolean onOverviewLongClick(View view) {
        AndroidUtils.addToClipboard("Overview", extraMovie.overview);
        Toast.makeText(getContext(), getString(R.string.ClipboardCopied, getString(R.string.Overview)), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onTrailerClick(View view, String trailerKey) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey)));
    }

    @Override
    public void onTrailersSectionClick(View view) {
        //activity.startTrailers(extraMovie, trailers);
    }

    @Override
    public void onMovieUrlClick(View view, int position) {
        if (position == 1) {
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