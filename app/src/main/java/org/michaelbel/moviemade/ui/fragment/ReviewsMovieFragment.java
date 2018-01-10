package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;

import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.rest.response.ReviewResponse;
import org.michaelbel.moviemade.ui.adapter.ReviewsAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.MovieActivity;
import org.michaelbel.moviemade.app.ApiFactory;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsMovieFragment extends MvpAppCompatFragment {

    private int page;
    private int totalPages;
    private boolean isLoading;
    private Movie currentMovie;

    private ReviewsAdapter adapter;
    private MovieActivity activity;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Review> reviews = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    //@InjectPresenter
    //public ReviewsMoviePresenter presenter;

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
        activity.binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (AndroidUtils.scrollToTop()) {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.notConnected()) {
                onLoadError();
            } else {
                if (reviews.isEmpty()) {
                    loadReviews();
                } else {
                    fragmentView.setRefreshing(false);
                }
            }
        });

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setVisibility(reviews.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(emptyView);

        page = 1;
        adapter = new ReviewsAdapter(reviews);
        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Review review = reviews.get(position);
            activity.startReview(review, currentMovie);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastVisibleItemPosition() == reviews.size() - 1 && !isLoading) {
                    if (page < totalPages) {
                        loadReviews();
                    }
                }
            }
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

        if (savedInstanceState == null) {
            if (NetworkUtils.notConnected()) {
                onLoadError();
            } else {
                loadReviews();
            }
        } else {
            reviews.clear();
            reviews.addAll(savedInstanceState.getParcelableArrayList("reviews"));
            onLoadSuccessful();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("reviews", reviews);
    }

    private void loadReviews() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<ReviewResponse> call = service.getReviews(currentMovie.id, Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    if (totalPages == 0) {
                        totalPages = response.body().totalPages;
                    }

                    List<Review> newReviews = new ArrayList<>();
                    newReviews.addAll(response.body().reviews);

                    reviews.clear(); // todo: Адский костыль
                    reviews.addAll(newReviews);
                    adapter.notifyItemRangeInserted(reviews.size() + 1, newReviews.size());

                    if (reviews.isEmpty()) {
                        emptyView.setMode(EmptyView.MODE_NO_REVIEWS);
                    } else {
                        page++;
                        isLoading = false;
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                isLoading = false;
                onLoadError();
            }
        });

        isLoading = true;
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
    }
}