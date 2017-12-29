package org.michaelbel.application.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.MovieResponse;
import org.michaelbel.application.ui.MainActivity;
import org.michaelbel.application.ui.MovieActivity;
import org.michaelbel.application.ui.adapter.MoviesListAdapter;
import org.michaelbel.application.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.AndroidUtilsDev;
import org.michaelbel.application.util.NetworkUtils;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class ListMoviesFragment extends Fragment {

    public static final int LIST_NOW_PLAYING = 1;
    public static final int LIST_POPULAR = 2;
    public static final int LIST_TOP_RATED = 3;
    public static final int LIST_UPCOMING = 4;
    public static final int LIST_SIMILAR = 5;
    public static final int LIST_RELATED = 6;

    private final int TOTAL_PAGES = 1000;

    private int page;
    private boolean isLoading;
    private int currentMovieList;
    private Movie currentMovie;

    private TextView emptyView;
    private SwipeRefreshLayout fragmentView;

    private MoviesListAdapter adapter;
    private GridLayoutManager layoutManager;
    private List<Movie> movieList = new ArrayList<>();

    public static ListMoviesFragment newInstance(int list) {
        Bundle args = new Bundle();
        args.putInt("list", list);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListMoviesFragment newInstance(int list, Movie movie) {
        Bundle args = new Bundle();
        args.putInt("list", list);
        args.putSerializable("movie", movie);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatActivity activity;
        if (getArguments().getSerializable("movie") != null) {
            activity = (MovieActivity) getActivity();
        } else {
            activity = (MainActivity) getActivity();
        }

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(movieList.isEmpty());
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (movieList.isEmpty()) {
                    loadList();
                } else {
                    fragmentView.setRefreshing(false);
                }
            }
        });

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        emptyView = new TextView(activity);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        page = 1;
        adapter = new MoviesListAdapter(activity, movieList);

        layoutManager = new GridLayoutManager(activity, AndroidUtils.getColumns());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerListView recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbarsEnabled());
        recyclerView.addItemDecoration(new PaddingItemDecoration(ScreenUtils.dp(2)));
        recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = movieList.get(position);
            if (getArguments().getSerializable("movie") != null) {
                ((MovieActivity) getActivity()).startMovie(movie);
            } else {
                ((MainActivity) getActivity()).startMovie(movie);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == movieList.size() - 1 && !isLoading) {
                    if (page < TOTAL_PAGES) {
                        loadList();
                    }
                }
            }
        });
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            currentMovieList = getArguments().getInt("list");

            if (getArguments().getSerializable("movie") != null) {
                currentMovie = (Movie) getArguments().getSerializable("movie");
            }
        }

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            loadList();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutManager.setSpanCount(AndroidUtils.getColumns());
    }

    private void loadList() {
        if (currentMovieList == LIST_NOW_PLAYING) {
            loadNowPlayingMovies();
        } else if (currentMovieList == LIST_POPULAR) {
            loadPopularMovies();
        } else if (currentMovieList == LIST_TOP_RATED) {
            loadTopRatedMovies();
        } else if (currentMovieList == LIST_UPCOMING) {
            loadUpcomingMovies();
        } else if (currentMovieList == LIST_SIMILAR) {
            loadSimilarMovies();
        } else if (currentMovieList == LIST_RELATED) {
            loadRelatedMovies();
        }
    }

    private void loadNowPlayingMovies() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<MovieResponse> call = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (AndroidUtils.includeAdult()) {
                        movieList.addAll(response.body().movieList);
                    } else {
                        for (Movie movie : response.body().movieList) {
                            if (!movie.adult) {
                                movieList.add(movie);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (movieList.isEmpty()) {
                        emptyView.setText(R.string.NoMovies);
                    } else {
                        page++;
                        isLoading = false;
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                onLoadError();
                isLoading = false;
            }
        });

        isLoading = true;
    }

    private void loadPopularMovies() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<MovieResponse> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (AndroidUtils.includeAdult()) {
                        movieList.addAll(response.body().movieList);
                    } else {
                        for (Movie movie : response.body().movieList) {
                            if (!movie.adult) {
                                movieList.add(movie);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (movieList.isEmpty()) {
                        emptyView.setText(R.string.NoMovies);
                    } else {
                        page++;
                        isLoading = false;
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                onLoadError();
                isLoading = false;
            }
        });

        isLoading = true;
    }

    private void loadTopRatedMovies() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<MovieResponse> call = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().movieList != null) {
                            List<Movie> list = response.body().movieList;

                            if (AndroidUtils.includeAdult()) {
                                movieList.addAll(list);
                            } else {
                                for(Movie movie : list) {
                                    if (!movie.adult) {
                                        movieList.add(movie);
                                    }
                                }
                            }
                        } else {
                            emptyView.setText(R.string.NoMovies);
                        }
                    }

                    adapter.notifyDataSetChanged();
                    page++;
                    isLoading = false;
                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                isLoading = false;
                onLoadError();
            }
        });

        isLoading = true;
    }

    private void loadUpcomingMovies() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<MovieResponse> call = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (AndroidUtils.includeAdult()) {
                        movieList.addAll(response.body().movieList);
                    } else {
                        for (Movie movie : response.body().movieList) {
                            if (!movie.adult) {
                                movieList.add(movie);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (movieList.isEmpty()) {
                        emptyView.setText(R.string.NoMovies);
                    } else {
                        page++;
                        isLoading = false;
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                onLoadError();
                isLoading = false;
            }
        });

        isLoading = true;
    }

    private void loadSimilarMovies() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        service.getSimilarMovies(currentMovie.id, Url.TMDB_API_KEY, Url.en_US, page)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            if (AndroidUtils.includeAdult()) {
                                movieList.addAll(response.body().movieList);
                            } else {
                                for (Movie movie : response.body().movieList) {
                                    if (!movie.adult) {
                                        movieList.add(movie);
                                    }
                                }
                            }

                            adapter.notifyDataSetChanged();

                            if (movieList.isEmpty()) {
                                emptyView.setText(R.string.NoMovies);
                            } else {
                                page++;
                                isLoading = false;
                            }

                            onLoadSuccessful();
                        } else {
                            onLoadError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        onLoadError();
                        isLoading = false;
                    }
                });

        isLoading = true;
    }

    private void loadRelatedMovies() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        service.getRecommendations(currentMovie.id, Url.TMDB_API_KEY, Url.en_US, page)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            if (AndroidUtils.includeAdult()) {
                                movieList.addAll(response.body().movieList);
                            } else {
                                for (Movie movie : response.body().movieList) {
                                    if (!movie.adult) {
                                        movieList.add(movie);
                                    }
                                }
                            }

                            adapter.notifyDataSetChanged();

                            if (movieList.isEmpty()) {
                                emptyView.setText(R.string.NoMovies);
                            } else {
                                page++;
                                isLoading = false;
                            }

                            onLoadSuccessful();
                        } else {
                            onLoadError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        onLoadError();
                        isLoading = false;
                    }
                });

        isLoading = true;
    }

    private void onLoadSuccessful() {
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        fragmentView.setRefreshing(false);
        emptyView.setText(R.string.NoConnection);
    }
}