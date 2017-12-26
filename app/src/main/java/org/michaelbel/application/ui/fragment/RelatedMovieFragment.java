package org.michaelbel.application.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.MovieResponse;
import org.michaelbel.application.ui.MovieActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.movie.MovieViewList;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelatedMovieFragment extends Fragment {

    private static final int TOTAL_PAGES = 1000;

    private int movieId;

    private int page;
    private boolean isLoading;

    private TextView emptyView;
    private SwipeRefreshLayout refreshLayout;

    private MovieActivity activity;
    private RelatedAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Movie> list = new ArrayList<>();

    public static RelatedMovieFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);

        RelatedMovieFragment fragment = new RelatedMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MovieActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_favs, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            movieId = getArguments().getInt("movieId");
        }

        emptyView = fragmentView.findViewById(R.id.empty_view);

        refreshLayout = fragmentView.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(Theme.accentColor());
        if (list.isEmpty()) {
            refreshLayout.setRefreshing(true);
        }
        refreshLayout.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (list.isEmpty()) {
                    getRelatedMovies();
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        page = 1;
        adapter = new RelatedAdapter();
        layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerListView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = list.get(position);
            activity.startMovie(movie.id, movie.title);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == list.size() - 1 && !isLoading) {
                    if (page < TOTAL_PAGES) {
                        getRelatedMovies();
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
            getRelatedMovies();
        }
    }

    private void getRelatedMovies() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        service.getRecommendations(movieId, Url.TMDB_API_KEY, Url.en_US, page)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            SharedPreferences prefs = activity.getSharedPreferences("main_config", Context.MODE_PRIVATE);
                            boolean adult = prefs.getBoolean("adult", true);

                            if (adult) {
                                list.addAll(response.body().movieList);
                            } else {
                                for (Movie movie : response.body().movieList) {
                                    if (!movie.adult) {
                                        list.add(movie);
                                    }
                                }
                            }

                            adapter.notifyDataSetChanged();

                            if (list.isEmpty()) {
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

    public class RelatedAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            return new Holder(new MovieViewList(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Movie movie = list.get(position);

            MovieViewList view = (MovieViewList) holder.itemView;
            view.setPoster(movie.posterPath)
                    .setTitle(movie.title)
                    .setYear(movie.releaseDate)
                    .setVoteAverage(movie.voteAverage)
                    .setDivider(position != list.size() - 1);
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }
    }
}