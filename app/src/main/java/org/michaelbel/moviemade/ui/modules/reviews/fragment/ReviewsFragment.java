package org.michaelbel.moviemade.ui.modules.reviews.fragment;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.entity.Review;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsAdapter;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsContract;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsPresenter;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewsActivity;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeReceiver;
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
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ReviewsFragment extends BaseFragment implements ReviewsContract.View, NetworkChangeListener {

    private Movie movie;
    private ReviewsAdapter adapter;
    private ReviewsActivity activity;
    private GridLayoutManager gridLayoutManager;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;
    private ReviewsContract.Presenter presenter;

    @Inject MoviesService service;
    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerListView recyclerView;

    public static ReviewsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(IntentsKt.MOVIE, movie);

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ReviewsActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
        Moviemade.get(activity).getComponent().injest(this);
        presenter = new ReviewsPresenter(this, service);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.getToolbar().setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

        int spanCount = activity.getResources().getInteger(R.integer.trailers_span_layout_count);

        adapter = new ReviewsAdapter();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        GridSpacingItemDecoration spacingDecoration = new GridSpacingItemDecoration(1, DeviceUtil.INSTANCE.dp(activity, 5));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(spacingDecoration);
        recyclerView.setOnItemClickListener((v, position) -> {
            Review review = adapter.getReviews().get(position);
            activity.startReview(review, movie);
        });

        Bundle args = getArguments();
        if (args != null) {
            movie = (Movie) args.getSerializable(IntentsKt.MOVIE);
        }

        if (movie != null) {
            presenter.getReviews(movie.getId());
        }
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
        presenter.getReviews(movie.getId());
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
            presenter.getReviews(movie.getId());
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