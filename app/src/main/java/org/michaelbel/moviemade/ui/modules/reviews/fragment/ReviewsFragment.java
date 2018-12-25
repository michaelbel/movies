package org.michaelbel.moviemade.ui.modules.reviews.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.entity.Review;
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsContract;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewsActivity;
import org.michaelbel.moviemade.ui.modules.reviews.adapter.OnReviewClickListener;
import org.michaelbel.moviemade.ui.modules.reviews.adapter.ReviewsAdapter;
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
import butterknife.BindView;

public class ReviewsFragment extends BaseFragment implements ReviewsContract.View, NetworkChangeListener, OnReviewClickListener {

    private Movie movie;
    private ReviewsAdapter adapter;
    private ReviewsActivity activity;

    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @Inject ReviewsContract.Presenter presenter;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

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

        Moviemade.get(activity).getFragmentComponent().inject(this);
        presenter.setView(this);
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

        adapter = new ReviewsAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 5)));

        emptyView.setOnClickListener(v -> {
            emptyView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            presenter.getReviews(movie.getId());
        });

        movie = getArguments() != null ? (Movie) getArguments().getSerializable(IntentsKt.MOVIE) : null;
        if (movie != null) {
            presenter.getReviews(movie.getId());
        }
    }

    @Override
    public void onReviewClick(@NotNull Review review, @NotNull View view) {
        activity.startReview(review, movie);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
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
}