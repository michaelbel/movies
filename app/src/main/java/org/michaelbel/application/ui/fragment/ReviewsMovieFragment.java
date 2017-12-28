package org.michaelbel.application.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Review;
import org.michaelbel.application.rest.response.ReviewResponse;
import org.michaelbel.application.ui.MovieActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.ReviewView;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.NetworkUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class ReviewsMovieFragment extends Fragment {

    private Movie currentMovie;

    private ReviewsAdapter adapter;
    private MovieActivity activity;
    private ArrayList<Review> reviewsList = new ArrayList<>();

    private TextView emptyView;
    private SwipeRefreshLayout fragmentView;

    public static ReviewsMovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);

        ReviewsMovieFragment fragment = new ReviewsMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MovieActivity) getActivity();

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(reviewsList.isEmpty());
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (reviewsList.isEmpty()) {
                    loadReviews();
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

        adapter = new ReviewsAdapter();

        RecyclerListView recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Review review = reviewsList.get(position);
            activity.startReview(review, currentMovie);
        });
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            currentMovie = (Movie) getArguments().getSerializable("movie");
        }

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            loadReviews();
        }
    }

    private void loadReviews() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<ReviewResponse> call = service.getReviews(currentMovie.id, Url.TMDB_API_KEY, Url.en_US, 1);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    if (!reviewsList.isEmpty()) {
                        reviewsList.clear();
                    }

                    reviewsList.addAll(response.body().reviewList);
                    adapter.notifyDataSetChanged();

                    if (reviewsList.isEmpty()) {
                        emptyView.setText(R.string.NoReviews);
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                onLoadError();
            }
        });
    }

    private void onLoadSuccessful() {
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        fragmentView.setRefreshing(false);
        emptyView.setText(R.string.NoConnection);
    }

    public class ReviewsAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new ReviewView(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Review review = reviewsList.get(position);

            ReviewView view = (ReviewView) holder.itemView;
            view.setAuthor(review.author)
                .setContent(review.content)
                .setDivider(position != reviewsList.size() - 1);
        }

        @Override
        public int getItemCount() {
            return reviewsList != null ? reviewsList.size() : 0;
        }
    }
}