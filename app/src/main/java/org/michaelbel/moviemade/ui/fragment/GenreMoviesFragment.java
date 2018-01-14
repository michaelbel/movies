package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
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

import org.michaelbel.moviemade.GenreActivity;
import org.michaelbel.moviemade.GenresActivity;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.rest.api.GENRES;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.GenreResponse;
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

public class GenreMoviesFragment extends Fragment {

    private int page;
    private int totalPages;
    private boolean isLoading;
    private int currentGenreId;

    private MoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;
    private List<Movie> movies = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    public static GenreMoviesFragment newInstance(int genreId) {
        Bundle args = new Bundle();
        args.putInt("genreId", genreId);

        GenreMoviesFragment fragment = new GenreMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof GenreActivity) {
            ((GenreActivity) getActivity()).binding.toolbarTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AndroidUtils.scrollToTop()) {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }
            });
        } else if (getActivity() instanceof GenresActivity) {
            ((GenresActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                    loadMoviesByGenre();
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
        //recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        //recyclerView.addItemDecoration(new PaddingItemDecoration(ScreenUtils.dp(2)));
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = movies.get(position);
            if (getActivity() instanceof GenresActivity) {
                ((GenresActivity) getActivity()).startMovie(movie);
            } else if (getActivity() instanceof GenreActivity) {
                ((GenreActivity) getActivity()).startMovie(movie);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (gridLayoutManager.findLastVisibleItemPosition() == movies.size() - 1 && !isLoading) {
                    if (page < totalPages) {
                        loadMoviesByGenre();
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

        currentGenreId = getArguments().getInt("genreId");

        if (NetworkUtils.notConnected()) {
            onLoadError();
        } else {
            loadMoviesByGenre();
        }
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(AndroidUtils.getColumns());
    }*/

    private void loadMoviesByGenre() {
        GENRES service = ApiFactory.createService(GENRES.class);
        Call<GenreResponse> call =  service.getMovies(currentGenreId, Url.TMDB_API_KEY, Url.en_US, AndroidUtils.includeAdult(), page);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
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
            public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable t) {
                isLoading = false;
                onLoadError();
            }
        });

        isLoading = true;
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
}