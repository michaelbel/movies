package org.michaelbel.application.ui.fragment;

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

public class ReviewsMovieFragment extends Fragment {

    private static final String MOVIE_ID = "movie_id";

    private int movieId;

    private ReviewsAdapter adapter;
    private MovieActivity activity;
    private ArrayList<Review> reviewList = new ArrayList<>();

    private TextView emptyView;
    private SwipeRefreshLayout refreshLayout;

    public static ReviewsMovieFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);

        ReviewsMovieFragment fragment = new ReviewsMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MovieActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_review, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        emptyView = fragmentView.findViewById(R.id.empty_view);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));

        refreshLayout = fragmentView.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(Theme.accentColor());
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(() -> {
            if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
                onLoadError();
            } else {
                if (reviewList.isEmpty()) {
                    getReviews();
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        adapter = new ReviewsAdapter();

        RecyclerListView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setOnItemClickListener((view, position) ->
                activity.startReview(reviewList.get(position).id)
        );

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(MOVIE_ID);
        }

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            getReviews();
        }
    }

    private void getReviews() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<ReviewResponse> call = service.getReviews(movieId, Url.TMDB_API_KEY, Url.en_US, 1);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    if (!reviewList.isEmpty()) {
                        reviewList.clear();
                    }

                    reviewList.addAll(response.body().reviewList);
                    adapter.notifyDataSetChanged();

                    if (reviewList.isEmpty()) {
                        emptyView.setText(R.string.NoReviews);
                    }

                    onLoadSuccessful();
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
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

    public class ReviewsAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new ReviewView(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Review review = reviewList.get(position);

            ReviewView view = (ReviewView) holder.itemView;
            view.setAuthor(review.author);
            view.setContent(review.content);
            view.setDivider(position != reviewList.size() - 1);
        }

        @Override
        public int getItemCount() {
            return reviewList != null ? reviewList.size() : 0;
        }
    }
}