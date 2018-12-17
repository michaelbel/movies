package org.michaelbel.moviemade.ui.modules.trailers;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Logger;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Video;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.ui.modules.trailers.adapter.TrailersAdapter;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.base.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.modules.trailers.dialog.YoutubePlayerDialogFragment;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import butterknife.BindView;
import butterknife.OnClick;

public class TrailersFragment extends BaseFragment implements NetworkChangeListener, TrailersContract.View {

    private static final String YOUTUBE_DIALOG_FRAGMENT_TAG = "youtubeFragment";

    private int movieId;
    private TrailersAdapter adapter;
    private TrailersActivity activity;
    private GridLayoutManager gridLayoutManager;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;
    private TrailersContract.Presenter presenter;

    @Inject MoviesService service;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerListView recyclerView;

    static TrailersFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.MOVIE_ID, movieId);

        TrailersFragment fragment = new TrailersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TrailersContract.Presenter getPresenter() {
        return presenter;
    }

    public RecyclerListView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (TrailersActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
        Moviemade.get(activity).getComponent().injest(this);

        presenter = new TrailersPresenter(this, service);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.getToolbar().setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);

        adapter = new TrailersAdapter();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
        //recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setOnItemClickListener((v, position) -> {
            Video trailer = adapter.trailers.get(position);
            YoutubePlayerDialogFragment dialog = YoutubePlayerDialogFragment.newInstance(String.valueOf(Uri.parse(trailer.getKey())));
            dialog.show(activity.getSupportFragmentManager(), YOUTUBE_DIALOG_FRAGMENT_TAG);
        });
        recyclerView.setOnItemLongClickListener((view1, position) -> {
            Video trailer = adapter.trailers.get(position);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey())));
            return true;
        });

        Bundle args = getArguments();
        if (args != null) {
            movieId = args.getInt(IntentsKt.MOVIE_ID);
        }

        presenter.getVideos(movieId);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_trailers;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshLayout();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
    }

    @OnClick(R.id.empty_view)
    void emptyViewClick(View v) {
        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        presenter.getVideos(movieId);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setTrailers(@NotNull List<Video> trailers) {
        connectionFailure = false;
        adapter.setTrailers(trailers);
    }

    @Override
    public void setError(@EmptyViewMode int mode) {
        connectionFailure = true;
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
        hideLoading();
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getVideos(movieId);
        }
    }

    private void refreshLayout() {
        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.onRestoreInstanceState(state);
    }
}