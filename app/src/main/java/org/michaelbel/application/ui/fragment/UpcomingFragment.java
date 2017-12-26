package org.michaelbel.application.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.moviemade.annotation.Beta;
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.MovieResponse;
import org.michaelbel.application.ui.MainActivity;
import org.michaelbel.application.ui.adapter.MoviesListAdapter;
import org.michaelbel.application.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AppUtils;
import org.michaelbel.application.util.NetworkUtils;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Beta
public class UpcomingFragment extends Fragment {

    private static final int TOTAL_PAGES = 1000;

    private int page;
    private boolean isLoading;

    private TextView emptyView;
    private SwipeRefreshLayout refreshLayout;

    private MainActivity activity;
    private MoviesListAdapter adapter;
    private GridLayoutManager layoutManager;
    private List<Movie> movieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_nowplaying, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        setHasOptionsMenu(true);

        emptyView = fragmentView.findViewById(R.id.empty_view);

        refreshLayout = fragmentView.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(Theme.accentColor());
        if (movieList.isEmpty()) {
            refreshLayout.setRefreshing(true);
        }
        refreshLayout.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (movieList.isEmpty()) {
                    getUpcoming();
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        page = 1;
        adapter = new MoviesListAdapter(activity, movieList);

        layoutManager = new GridLayoutManager(getContext(), AppUtils.getColumns());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerListView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        recyclerView.addItemDecoration(new PaddingItemDecoration(ScreenUtils.dp(2)));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = movieList.get(position);
            activity.startMovie(movie.id, movie.title);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == movieList.size() - 1 && !isLoading) {
                    if (page < TOTAL_PAGES) {
                        getUpcoming();
                    }
                }
            }
        });

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            getUpcoming();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutManager.setSpanCount(AppUtils.getColumns());
    }

    private void getUpcoming() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<MovieResponse> call = service.getUpcoming(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    SharedPreferences prefs = activity.getSharedPreferences("main_config", Context.MODE_PRIVATE);
                    boolean adult = prefs.getBoolean("adult", true);

                    if (adult) {
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
                    //FirebaseCrash.report(new Error("Server not found"));
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //FirebaseCrash.report(t);
                onLoadError();
                isLoading = false;
            }
        });

        isLoading = true;
    }

    private void onLoadSuccessful() {
        refreshLayout.setRefreshing(false);
    }

    private void onLoadError() {
        refreshLayout.setRefreshing(false);
        emptyView.setText(R.string.NoConnection);
    }
}