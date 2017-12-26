package org.michaelbel.application.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.moviemade.browser.Browser;
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Backdrop;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Poster;
import org.michaelbel.application.rest.response.CreditResponse;
import org.michaelbel.application.rest.response.ImageResponse;
import org.michaelbel.application.rest.response.VideoResponse;
import org.michaelbel.application.sqlite.DatabaseHelper;
import org.michaelbel.application.ui.MovieActivity;
import org.michaelbel.application.ui.TrailersActivity;
import org.michaelbel.application.ui.view.FavButton;
import org.michaelbel.application.ui.view.MovieInfoLayout;
import org.michaelbel.application.util.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private int movieId;
    private Movie currentMovie;
    private MovieActivity activity;

    private TextView emptyView;
    private ProgressBar progressBar;
    private MovieInfoLayout movieView;
    private NestedScrollView nestedScrollView;

    public static MovieFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity = (MovieActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_movie, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        setHasOptionsMenu(true);

        nestedScrollView = fragmentView.findViewById(R.id.scroll_view);

        movieView = new MovieInfoLayout(getContext());
        movieView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        nestedScrollView.addView(movieView);

        if (getArguments() != null) {
            movieId = getArguments().getInt("movieId");
        }

        emptyView = fragmentView.findViewById(R.id.empty_view);
        emptyView.setText(R.string.NoConnection);
        emptyView.setVisibility(View.INVISIBLE);

        progressBar = fragmentView.findViewById(R.id.progress_bar);

        movieView.setVisibility(View.GONE);
        movieView.setListener(new MovieInfoLayout.InfoMovieListener() {
            @Override
            public boolean onOverviewLongClick(View view) {
                return true;
            }

            @Override
            public void onTrailersSectionClick(View view) {
                Intent intent = new Intent(activity, TrailersActivity.class);
                intent.putExtra("movieId", movieId);
                intent.putExtra("movieTitle", currentMovie.title);
                startActivity(intent);

            }

            @Override
            public void onTrailerClick(View view, String trailerKey) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey)));
            }

            @Override
            public boolean onTrailerLongClick(View view, String trailerKey) {
                return true;
            }

            @Override
            public void onMovieUrlClick(View view, int position) {
                if (position == 1) {
                    Browser.openUrl(activity, "https://themoviedb.org/movie/" + movieId);
                } else if (position == 2) {
                    Browser.openUrl(activity, "http://imdb.com/title/" + currentMovie.imdbId);
                } else if (position == 3) {
                    Browser.openUrl(activity, currentMovie.homepage);
                }
            }

            @Override
            public void onFavButtonClick(View view) {
                DatabaseHelper database = DatabaseHelper.getInstance(activity);
                boolean isExist = database.isMovieExist(movieId);

                if (isExist) {
                    database.removeMovie(movieId);
                    if (view instanceof FavButton) {
                        ((FavButton) view).setIcon(R.drawable.ic_heart_outline);
                        ((FavButton) view).setText(R.string.Add);
                    }
                } else {
                    database.addMovie(currentMovie);
                    if (view instanceof FavButton) {
                        ((FavButton) view).setIcon(R.drawable.ic_heart);
                        ((FavButton) view).setText(R.string.Remove);
                    }
                }
            }
        });
        movieView.setCallback(() -> {
            progressBar.setVisibility(View.INVISIBLE);
            movieView.setVisibility(View.VISIBLE);
        });

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            progressBar.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            loadMovieDetails(movieId);
        }
    }

    private void loadMovieDetails(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<Movie> call = service.getDetails(movieId, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    currentMovie = response.body();
                    displayMovie(currentMovie);
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                //FirebaseCrash.report(t);
            }
        });
    }

    private void displayMovie(Movie movie) {
        movieView.setPoster(movie.posterPath);
        movieView.setTitle(movie.title);
        movieView.setGenres(movie.genresList);
        movieView.setDate(movie.releaseDate);
        movieView.setRuntime(movie.runtime);
        movieView.setCountries(movie.countriesList);
        movieView.setStatus(movie.status);
        movieView.setTagline(movie.tagline);
        movieView.setBudget(movie.budget);
        movieView.setRevenue(movie.revenue);
        movieView.setOriginalTitle(movie.originalTitle);
        movieView.setOriginalLang(movie.originalLanguage);
        movieView.setCompanies(movie.companiesList);
        movieView.setVoteAverage(movie.voteAverage);
        movieView.setVoteCount(movie.voteCount);
        movieView.setOverview(movie.overview);
        movieView.setHomePage(movie.homepage);
        movieView.setFavButton(movie.id);

        loadTrailers(movie.id);
        loadImages(movie.id);
        loadCredits(movie.id);
    }

    private void loadTrailers(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<VideoResponse> call = service.getVideos(movieId, Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    movieView.setTrailers(response.body().trailersList);
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                //FirebaseCrash.report(t);
            }
        });
    }

    private void loadImages(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<ImageResponse> call = service.getImages(movieId, Url.TMDB_API_KEY, null);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    List<Poster> posterList = response.body().postersList;
                    List<Backdrop> backdropList = response.body().backdropsList;

                    String poster1 = posterList.get(0).filePath;
                    String backdrop1 = backdropList.get(0).filePath;

                    movieView.setImages(poster1, backdrop1, posterList.size(), backdropList.size());
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                //FirebaseCrash.report(t);
            }
        });
    }

    private void loadCredits(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<CreditResponse> call = service.getCredits(movieId, Url.TMDB_API_KEY);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                if (response.isSuccessful()) {
                    movieView.setCrew(response.body().crewList);
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                //FirebaseCrash.report(t);
            }
        });
    }
}