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
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.presenter.ReviewsMoviePresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.MovieActivity;
import org.michaelbel.moviemade.ui.adapter.ReviewsAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

import java.util.List;

public class ReviewsFragment extends MvpAppCompatFragment implements MvpResultsView {

    private int movieId;
    private Movie currentMovie;
    private MovieRealm currentMovieRealm;

    private ReviewsAdapter adapter;
    private MovieActivity activity;
    private LinearLayoutManager linearLayoutManager;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    @InjectPresenter
    public ReviewsMoviePresenter presenter;

    public static ReviewsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ReviewsFragment newInstance(MovieRealm movie) {
        Bundle args = new Bundle();
        args.putParcelable("movieRealm", movie);

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MovieActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            if (adapter.getReviews().isEmpty()) {
                presenter.loadFirstPage(movieId);
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(emptyView);

        adapter = new ReviewsAdapter();
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Review review = (Review) adapter.getReviews().get(position);
            if (currentMovie != null) {
                activity.startReview(review, currentMovie);
            } else {
                activity.startReview(review, currentMovieRealm);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastVisibleItemPosition() == adapter.getReviews().size() - 1 && !presenter.loading && !presenter.loadingLocked) {
                    if (presenter.page < presenter.totalPages) {
                        presenter.loadNextPage();
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

        if (getArguments() == null) {
            return;
        }

        currentMovie = (Movie) getArguments().getSerializable("movie");
        currentMovieRealm = getArguments().getParcelable("movieRealm");
        if (currentMovie != null) {
            movieId = currentMovie.id;
        } else if (currentMovieRealm != null) {
            movieId = currentMovieRealm.id;
        } else {
            movieId = 0;
        }

        if (savedInstanceState == null) {
            presenter.loadFirstPage(movieId);
        }
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        adapter.addReviews(results);
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int mode) {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
    }
}