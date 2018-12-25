package org.michaelbel.moviemade.ui.modules.trailers;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Video;
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.trailers.adapter.OnTrailerClickListener;
import org.michaelbel.moviemade.ui.modules.trailers.adapter.TrailersAdapter;
import org.michaelbel.moviemade.ui.modules.trailers.dialog.YoutubePlayerDialogFragment;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersFragment extends BaseFragment implements NetworkChangeListener, TrailersContract.View, OnTrailerClickListener {

    private static final String YOUTUBE_DIALOG_FRAGMENT_TAG = "youtubeFragment";

    private int movieId;
    private TrailersAdapter adapter;
    private TrailersActivity activity;

    private boolean connectionFailure = false;
    private NetworkChangeReceiver networkChangeReceiver;

    @Inject TrailersContract.Presenter presenter;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    static TrailersFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.MOVIE_ID, movieId);

        TrailersFragment fragment = new TrailersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (TrailersActivity) getActivity();

        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));

        Moviemade.get(activity).getFragmentComponent().inject(this);
        presenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trailers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.getToolbar().setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);

        adapter = new TrailersAdapter(this);

        progressBar = view.findViewById(R.id.progress_bar);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 5)));

        emptyView = view.findViewById(R.id.empty_view);
        emptyView.setOnClickListener(v -> {
            emptyView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            presenter.getVideos(movieId);
        });

        movieId = getArguments() != null ? getArguments().getInt(IntentsKt.MOVIE_ID) : 0;
        presenter.getVideos(movieId);
    }

    @Override
    public void onTrailerClick(@NotNull Video video, @NotNull View view) {
        YoutubePlayerDialogFragment dialog = YoutubePlayerDialogFragment.newInstance(String.valueOf(Uri.parse(video.getKey())));
        dialog.show(activity.getSupportFragmentManager(), YOUTUBE_DIALOG_FRAGMENT_TAG);
    }

    @Override
    public boolean onTrailerLongClick(@NotNull Video video, @NotNull View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getKey())));
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
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
        adapter.addTrailers(trailers);
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
}