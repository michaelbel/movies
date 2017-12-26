package org.michaelbel.application.ui.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.rest.response.VideoResponse;
import org.michaelbel.application.ui.TrailersActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.application.ui.view.trailer.TrailerCompatView;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AppUtils;
import org.michaelbel.application.util.NetworkUtils;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailersFragment extends Fragment {

    private int movieId;

    private TrailersActivity activity;
    private VideosAdapter adapter;
    private GridLayoutManager layoutManager;
    private ArrayList<Trailer> trailerList = new ArrayList<>();

    private TextView emptyView;
    private SwipeRefreshLayout refreshLayout;

    public static TrailersFragment newInstance(int movieId, String movieTitle) {
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);
        args.putString("movieTitle", movieTitle);

        TrailersFragment fragment = new TrailersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (TrailersActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_videos, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finish());

        activity.titleView.setTitle("Trailers");
        if (getArguments() != null) {
            activity.titleView.setSubtitle(getArguments().getString("movieTitle"));
        }

        emptyView = fragmentView.findViewById(R.id.empty_view);

        refreshLayout = fragmentView.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(Theme.accentColor());
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (trailerList.isEmpty()) {
                    loadVideos();
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        adapter = new VideosAdapter();
        layoutManager = new GridLayoutManager(activity, AppUtils.getColumnsForVideos());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerListView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new PaddingItemDecoration(ScreenUtils.dp(4)));
        recyclerView.setOnItemClickListener((view, position) -> {
            Trailer trailer = trailerList.get(position);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.key)));
        });

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt("movieId");
        }

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            loadVideos();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutManager.setSpanCount(AppUtils.getColumnsForVideos());
    }

    private void loadVideos() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<VideoResponse> call = service.getVideos(movieId, Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    trailerList.addAll(response.body().trailersList);
                    adapter.notifyDataSetChanged();

                    if (trailerList.isEmpty()) {
                        emptyView.setText(R.string.NoVideos);
                    }

                    onLoadSuccessful();
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                //FirebaseCrash.report(t);
                onLoadError();
            }
        });
    }

    private void onLoadSuccessful() {
        refreshLayout.setRefreshing(false);
    }

    private void onLoadError() {
        refreshLayout.setRefreshing(false);
        emptyView.setText(R.string.NoConnection);
    }

    private class VideosAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new TrailerCompatView(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Trailer trailer = trailerList.get(position);

            TrailerCompatView view = (TrailerCompatView) holder.itemView;
            view.setTitle(trailer.name)
                .setQuality(trailer.size)
                .setSite(trailer.site)
                .setTrailerImage(trailer.key);
        }

        @Override
        public int getItemCount() {
            return trailerList != null ? trailerList.size() : 0;
        }
    }
}