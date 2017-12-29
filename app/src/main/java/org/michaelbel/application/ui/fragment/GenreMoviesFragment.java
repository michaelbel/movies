package org.michaelbel.application.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import org.michaelbel.application.rest.api.GENRES;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.GenreResponse;
import org.michaelbel.application.ui.GenresActivity;
import org.michaelbel.application.ui.adapter.MoviesListAdapter;
import org.michaelbel.application.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.NetworkUtils;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class GenreMoviesFragment extends Fragment {

    private final int TOTAL_PAGES = 1000;

    private int page;
    private boolean isLoading;
    private int currentGenreId;

    private TextView emptyView;
    private SwipeRefreshLayout fragmentView;

    private GenresActivity activity;
    private MoviesListAdapter adapter;
    private GridLayoutManager layoutManager;
    private List<Movie> movieList = new ArrayList<>();

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
        activity = (GenresActivity) getActivity();

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(movieList.isEmpty());
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (movieList.isEmpty()) {
                    loadMoviesByGenre();
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
        recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        recyclerView.addItemDecoration(new PaddingItemDecoration(ScreenUtils.dp(2)));
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = movieList.get(position);
            activity.startMovie(movie);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == movieList.size() - 1 && !isLoading) {
                    if (page < TOTAL_PAGES) {
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

        if (getArguments() != null) {
            currentGenreId = getArguments().getInt("genreId");
        }

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            loadMoviesByGenre();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutManager.setSpanCount(AndroidUtils.getColumns());
    }

    private void loadMoviesByGenre() {
        GENRES service = ApiFactory.getRetrofit().create(GENRES.class);
        Call<GenreResponse> call =  service.getMovies(currentGenreId, Url.TMDB_API_KEY, Url.en_US, AndroidUtils.includeAdult(), page);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                if (response.isSuccessful()) {
                    movieList.addAll(response.body().movieList);
                    adapter.notifyDataSetChanged();

                    if (movieList.isEmpty()) {
                        emptyView.setText(R.string.NoMovies);
                    } else {
                        isLoading = false;
                    }

                    page++;
                    onLoadSuccessful();
                } else {
                    // todo Error.
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable t) {
                // todo Error.
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