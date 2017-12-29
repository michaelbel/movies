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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.TrailersActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.trailer.TrailerCompatView;
import org.michaelbel.application.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.AndroidUtilsDev;
import org.michaelbel.application.util.NetworkUtils;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;

@SuppressWarnings("all")
public class TrailersFragment extends Fragment {

    private Movie currentMovie;
    private ArrayList<Trailer> trailersList;

    private VideosAdapter adapter;
    private TrailersActivity activity;
    private GridLayoutManager layoutManager;

    private TextView emptyView;
    private SwipeRefreshLayout fragmentView;

    public static TrailersFragment newInstance(Movie movie, ArrayList<Trailer> list) {
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        args.putSerializable("list", list);

        TrailersFragment fragment = new TrailersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (TrailersActivity) getActivity();

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.titleView.setTitle(R.string.Trailers);

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(true);
        //fragmentView.setRefreshing(trailersList.isEmpty()); //  null object
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (trailersList.isEmpty()) {
                    loadVideos();
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

        adapter = new VideosAdapter();
        layoutManager = new GridLayoutManager(activity, AndroidUtils.getColumnsForVideos());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerListView recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbarsEnabled());
        recyclerView.addItemDecoration(new PaddingItemDecoration(ScreenUtils.dp(2)));
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Trailer trailer = trailersList.get(position);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.key)));
        });
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            currentMovie = (Movie) getArguments().getSerializable("movie");
            trailersList = (ArrayList<Trailer>) getArguments().getSerializable("list");
        }

        activity.titleView.setSubtitle(currentMovie.title);

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            loadVideos();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutManager.setSpanCount(AndroidUtils.getColumnsForVideos());
    }

    private void loadVideos() {
        if (trailersList != null) {
            if (!trailersList.isEmpty()) {
                adapter.notifyDataSetChanged();
            } else {
                emptyView.setText(R.string.NoTrailers);
            }
            onLoadSuccessful();
        } else {
            onLoadError();
        }
    }

    private void onLoadSuccessful() {
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        fragmentView.setRefreshing(false);
        emptyView.setText(R.string.NoConnection);
    }

    private class VideosAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new TrailerCompatView(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Trailer trailer = trailersList.get(position);

            TrailerCompatView view = (TrailerCompatView) holder.itemView;
            view.setTitle(trailer.name)
                .setQuality(trailer.size)
                .setSite(trailer.site)
                .setTrailerImage(trailer.key)
                .changeLayoutParams();
        }

        @Override
        public int getItemCount() {
            return trailersList != null ? trailersList.size() : 0;
        }
    }
}