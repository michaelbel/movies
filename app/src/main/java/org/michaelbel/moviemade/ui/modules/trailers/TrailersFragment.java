package org.michaelbel.moviemade.ui.modules.trailers;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Video;
import org.michaelbel.moviemade.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.base.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.modules.trailers.dialog.YoutubePlayerDialogFragment;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import butterknife.BindView;
import butterknife.OnClick;

public class TrailersFragment extends BaseFragment implements TrailersMvp, NetworkChangeListener {

    private static final String YOUTUBE_DIALOG_FRAGMENT_TAG = "youtubeFragment";

    private TrailersAdapter adapter;
    private TrailersActivity activity;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    // TODO make private.
    // TODO make add getter
    @InjectPresenter public TrailersPresenter presenter;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    // TODO make private.
    // TODO make add getter
    @BindView(R.id.recycler_view) public RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (TrailersActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(DeviceUtil.INSTANCE.dp(activity, 4));

        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);

        adapter = new TrailersAdapter();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setPadding(0, DeviceUtil.INSTANCE.dp(activity,2), 0, DeviceUtil.INSTANCE.dp(activity,2));
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
        presenter.getVideos(activity.movie.getId());
    }

    @Override
    public void setTrailers(@NotNull List<Video> trailers) {
        connectionFailure = false;
        adapter.setTrailers(trailers);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError(@EmptyViewMode int mode) {
        connectionFailure = true;
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getVideos(activity.movie.getId());
        }
    }

    private void refreshLayout() {
        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        itemDecoration.setOffset(0);
        recyclerView.addItemDecoration(itemDecoration);
        gridLayoutManager.onRestoreInstanceState(state);
    }
}