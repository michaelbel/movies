package org.michaelbel.moviemade.ui.modules.reviews.fragment;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.base.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsAdapter;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsMvp;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsPresenter;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewsActivity;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReviewsFragment extends MvpAppCompatFragment implements ReviewsMvp, NetworkChangeListener {

    private Unbinder unbinder;
    private ReviewsAdapter adapter;
    private ReviewsActivity activity;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @InjectPresenter
    public ReviewsPresenter presenter;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) public RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ReviewsActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(DeviceUtil.INSTANCE.dp(activity, 4));

        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);

        adapter = new ReviewsAdapter();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);

        emptyView.setOnClickListener(v -> {
            emptyView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            presenter.getReviews((Moviemade) activity.getApplication(), activity.movie.getId());
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setPadding(0, DeviceUtil.INSTANCE.dp(activity,2), 0, DeviceUtil.INSTANCE.dp(activity,2));
        recyclerView.setOnItemClickListener((v, position) -> {
            Review review = adapter.reviews.get(position);
            activity.startReview(review, activity.movie);
        });
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
        unbinder.unbind();
    }

    @Override
    public void setReviews(@NotNull List<Review> reviews) {
        connectionFailure = false;
        adapter.setReviews(reviews);
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
            presenter.getReviews((Moviemade) activity.getApplication(), activity.movie.getId());
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