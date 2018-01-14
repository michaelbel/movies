package org.michaelbel.moviemade.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.eventbus.Events;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.MoviePeopleResponse;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.MainActivity;
import org.michaelbel.moviemade.MovieActivity;
import org.michaelbel.moviemade.PersonActivity;
import org.michaelbel.moviemade.ui.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;
import org.michaelbel.moviemade.util.NetworkUtils;
import org.michaelbel.moviemade.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class ListMoviesFragment extends Fragment {

    public static final int LIST_NOW_PLAYING = 1;
    public static final int LIST_POPULAR = 2;
    public static final int LIST_TOP_RATED = 3;
    public static final int LIST_UPCOMING = 4;
    public static final int LIST_SIMILAR = 5;
    public static final int LIST_RELATED = 6;
    public static final int LIST_BY_PERSON = 7;

    private final int TOTAL_PAGES = 1000;

    private int page;
    private int totalPages;
    private boolean isLoading;
    private int currentMovieList;
    private Movie currentMovie;
    private Cast currentPerson;

    private int movieId;
    private MovieRealm currentMovieRealm;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    private MoviesAdapter adapter;
    private PaddingItemDecoration itemDecoration;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private List<Movie> movies = new ArrayList<>();

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

    public static ListMoviesFragment newInstance(int list, MovieRealm movie) {
        Bundle args = new Bundle();
        args.putInt("list", list);
        args.putParcelable("movieRealm", movie);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListMoviesFragment newInstance(int list, Cast person) {
        Bundle args = new Bundle();
        args.putInt("list", list);
        args.putSerializable("person", person);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof MovieActivity) {
            ((MovieActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if (AndroidUtils.scrollToTop()) {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }
            });
        } else if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if (AndroidUtils.scrollToTop()) {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }
            });
        } else if (getActivity() instanceof PersonActivity) {
            ((PersonActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if (AndroidUtils.scrollToTop()) {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }
            });
        }

        fragmentView = new SwipeRefreshLayout(getContext());
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(getContext(), Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.notConnected()) {
                onLoadError();
            } else {
                if (movies.isEmpty()) {
                    if (currentMovieList == LIST_BY_PERSON) {
                        loadPersonMovies();
                    } else {
                        loadList();
                    }
                } else {
                    fragmentView.setRefreshing(false);
                }
            }
        });

        FrameLayout contentLayout = new FrameLayout(getContext());
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setVisibility(movies.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        page = 1;
        adapter = new MoviesAdapter(movies);

        itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.getSpanForMovies());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = movies.get(position);

            if (getArguments().getSerializable("movie") != null) {
                ((MovieActivity) getActivity()).startMovie(movie);
            } else if (getArguments().getSerializable("person") != null) {
                ((PersonActivity) getActivity()).startMovie(movie);
            } else {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).startMovie(movie);
                } else if (getActivity() instanceof MovieActivity) {
                    ((MovieActivity) getActivity()).startMovie(movie);
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (gridLayoutManager.findLastVisibleItemPosition() == movies.size() - 1 && !isLoading) {
                    if (currentMovieList != LIST_BY_PERSON) {
                        if (page < TOTAL_PAGES) {
                            loadList();
                        }
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

        if (getArguments() == null) {
            return;
        }

        currentMovieList = getArguments().getInt("list");
        currentMovie = (Movie) getArguments().getSerializable("movie");
        currentMovieRealm = (MovieRealm) getArguments().getParcelable("movieRealm");

        if (currentMovie != null) {
            movieId = currentMovie.id;
        } else if (currentMovieRealm != null) {
            movieId = currentMovieRealm.id;
        }

        if (getArguments().getSerializable("person") != null) {
            currentPerson = (Cast) getArguments().getSerializable("person");
        }

        if (NetworkUtils.notConnected()) {
            onLoadError();
        } else {
            if (currentMovieList == LIST_BY_PERSON) {
                loadPersonMovies();
            } else {
                loadList();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ((Moviemade) getActivity().getApplication()).eventBus().toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object object) {
                if (object instanceof Events.MovieListUpdateAdult) {
                    if (!NetworkUtils.notConnected()) {
                        if (!movies.isEmpty()) {
                            movies.clear();
                        }

                        if (currentMovieList == LIST_BY_PERSON) {
                            loadPersonMovies();
                        } else {
                            loadList();
                        }
                    }
                } else if (object instanceof Events.MovieListUpdateImageQuality) {
                    if (!NetworkUtils.notConnected()) {
                        if (!movies.isEmpty()) {
                            movies.clear();
                        }

                        if (currentMovieList == LIST_BY_PERSON) {
                            loadPersonMovies();
                        } else {
                            loadList();
                        }
                    }
                } else if (object instanceof Events.MovieListRefreshLayout) {
                    refreshLayout();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(1);
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
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getNowPlaying(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    onLoadError();
                    return;
                }

                if (totalPages == 0) {
                    totalPages = response.body().totalPages;
                }

                List<Movie> newMovies = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    newMovies.addAll(response.body().movies);
                } else {
                    for (Movie movie : response.body().movies) {
                        if (!movie.adult) {
                            newMovies.add(movie);
                        }
                    }
                }

                movies.addAll(newMovies);
                adapter.notifyItemRangeInserted(movies.size() + 1, newMovies.size());

                if (movies.isEmpty()) {
                    emptyView.setMode(EmptyView.MODE_NO_MOVIES);
                } else {
                    page++;
                    isLoading = false;
                }

                onLoadSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                isLoading = false;
                onLoadError();
            }
        });

        isLoading = true;
    }

    private void loadPopularMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    onLoadError();
                    return;
                }

                if (totalPages == 0) {
                    totalPages = response.body().totalPages;
                }

                List<Movie> newMovies = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    newMovies.addAll(response.body().movies);
                } else {
                    for (Movie movie : response.body().movies) {
                        if (!movie.adult) {
                            newMovies.add(movie);
                        }
                    }
                }

                movies.addAll(newMovies);
                adapter.notifyItemRangeInserted(movies.size() + 1, newMovies.size());

                if (movies.isEmpty()) {
                    emptyView.setMode(EmptyView.MODE_NO_MOVIES);
                } else {
                    page++;
                    isLoading = false;
                }

                onLoadSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                isLoading = false;
                onLoadError();
            }
        });

        isLoading = true;
    }

    private void loadTopRatedMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getTopRated(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    onLoadError();
                    return;
                }

                if (totalPages == 0) {
                    totalPages = response.body().totalPages;
                }

                List<Movie> newMovies = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    newMovies.addAll(response.body().movies);
                } else {
                    for(Movie movie : response.body().movies) {
                        if (!movie.adult) {
                            newMovies.add(movie);
                        }
                    }
                }

                movies.addAll(newMovies);
                adapter.notifyItemRangeInserted(movies.size() + 1, newMovies.size());

                if (movies.isEmpty()) {
                    emptyView.setMode(EmptyView.MODE_NO_MOVIES);
                } else {
                    page++;
                    isLoading = false;
                }

                onLoadSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                isLoading = false;
                onLoadError();
            }
        });

        isLoading = true;
    }

    private void loadUpcomingMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Call<MovieResponse> call = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (!response.isSuccessful()) {
                    onLoadError();
                    return;
                }

                if (totalPages == 0) {
                    totalPages = response.body().totalPages;
                }

                List<Movie> newMovies = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    newMovies.addAll(response.body().movies);
                } else {
                    for (Movie movie : response.body().movies) {
                        if (!movie.adult) {
                            newMovies.add(movie);
                        }
                    }
                }

                movies.addAll(newMovies);
                adapter.notifyItemRangeInserted(movies.size() + 1, newMovies.size());

                if (movies.isEmpty()) {
                    emptyView.setMode(EmptyView.MODE_NO_MOVIES);
                } else {
                    page++;
                    isLoading = false;
                }

                onLoadSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                isLoading = false;
                onLoadError();

            }
        });

        isLoading = true;
    }

    private void loadSimilarMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        service.getSimilarMovies(movieId, Url.TMDB_API_KEY, Url.en_US, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (!response.isSuccessful()) {
                            onLoadError();
                            return;
                        }

                        if (totalPages == 0) {
                            totalPages = response.body().totalPages;
                        }

                        List<Movie> newMovies = new ArrayList<>();

                        if (AndroidUtils.includeAdult()) {
                            newMovies.addAll(response.body().movies);
                        } else {
                            for (Movie movie : response.body().movies) {
                                if (!movie.adult) {
                                    newMovies.add(movie);
                                }
                            }
                        }

                        movies.clear();
                        movies.addAll(newMovies);
                        adapter.notifyItemRangeInserted(movies.size() + 1, newMovies.size());

                        if (movies.isEmpty()) {
                            emptyView.setMode(EmptyView.MODE_NO_MOVIES);
                        } else {
                            page++;
                            isLoading = false;
                        }

                        onLoadSuccessful();
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        isLoading = false;
                        onLoadError();
                    }
                });

        isLoading = true;
    }

    private void loadRelatedMovies() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (!response.isSuccessful()) {
                            onLoadError();
                            return;
                        }

                        if (totalPages == 0) {
                            totalPages = response.body().totalPages;
                        }

                        List<Movie> newMovies = new ArrayList<>();

                        if (AndroidUtils.includeAdult()) {
                            newMovies.addAll(response.body().movies);
                        } else {
                            for (Movie movie : response.body().movies) {
                                if (!movie.adult) {
                                    newMovies.add(movie);
                                }
                            }
                        }

                        movies.clear();
                        movies.addAll(newMovies);
                        adapter.notifyItemRangeInserted(movies.size() + 1, newMovies.size());

                        if (movies.isEmpty()) {
                            emptyView.setMode(EmptyView.MODE_NO_MOVIES);
                        } else {
                            page++;
                            isLoading = false;
                        }

                        onLoadSuccessful();
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        isLoading = false;
                        onLoadError();
                    }
                });

        isLoading = true;
    }

    private void loadPersonMovies() {
        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Call<MoviePeopleResponse> call = service.getMovieCredits(currentPerson.castId, Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<MoviePeopleResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviePeopleResponse> call, @NonNull Response<MoviePeopleResponse> response) {
                if (!response.isSuccessful()) {
                    // todo Error
                    return;
                }

                List<Movie> crewMovies = response.body().crewMovies;
                List<Movie> castMovies = response.body().castMovies;

                if (AndroidUtils.includeAdult()) {
                    movies.addAll(crewMovies);
                    movies.addAll(castMovies);
                } else {
                    for (Movie movie : crewMovies) {
                        if (!movie.adult) {
                            movies.add(movie);
                        }
                    }

                    for (Movie movie : castMovies) {
                        if (!movie.adult) {
                            movies.add(movie);
                        }
                    }
                }

                adapter.notifyDataSetChanged();

                if (movies.isEmpty()) {
                    emptyView.setMode(EmptyView.MODE_NO_MOVIES);
                }

                onLoadSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<MoviePeopleResponse> call, @NonNull Throwable t) {
                // todo Error.
            }
        });
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
    }

    private void refreshLayout() {
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.getSpanForMovies());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
            recyclerView.addItemDecoration(itemDecoration);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
            recyclerView.addItemDecoration(itemDecoration);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }
}