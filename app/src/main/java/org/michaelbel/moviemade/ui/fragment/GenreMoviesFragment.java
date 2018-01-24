package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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

import org.michaelbel.moviemade.mvp.presenter.GenreMoviesPresenter;
import org.michaelbel.moviemade.ui.GenreActivity;
import org.michaelbel.moviemade.ui.GenresActivity;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.mvp.view.MvpResultsView2;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.adapter.pagination.PaginationMoviesAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

import java.util.List;

public class GenreMoviesFragment extends MvpAppCompatFragment implements MvpResultsView2 {

    private int genreId;

    private PaginationMoviesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    @InjectPresenter
    public GenreMoviesPresenter presenter;

    public static GenreMoviesFragment newInstance(int genreId) {
        Bundle args = new Bundle();
        args.putInt("genreId", genreId);

        GenreMoviesFragment fragment = new GenreMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof GenreActivity) {
            ((GenreActivity) getActivity()).binding.toolbarTitle.setOnClickListener(v -> {
                if (AndroidUtils.scrollToTop()) {
                    recyclerView.smoothScrollToPosition(0);
                }
            });
        } else if (getActivity() instanceof GenresActivity) {
            ((GenresActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        }

        fragmentView = new SwipeRefreshLayout(getContext());
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(getContext(), Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (adapter.getMovies().isEmpty()) {
                presenter.loadFirstPage(genreId);
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        FrameLayout contentLayout = new FrameLayout(getContext());
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        adapter = new PaginationMoviesAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getMovies().get(position);
            if (view instanceof MovieViewListBig) {
                if (getActivity() instanceof GenresActivity) {
                    ((GenresActivity) getActivity()).startMovie(movie);
                } else if (getActivity() instanceof GenreActivity) {
                    ((GenreActivity) getActivity()).startMovie(movie);
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = linearLayoutManager.getItemCount();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!presenter.isLoading && !presenter.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0/* && totalItemCount >= presenter.totalPages*/) {
                        presenter.isLoading = true;
                        presenter.page++;
                        presenter.loadNextPage(genreId);
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

        genreId = getArguments().getInt("genreId");
        presenter.loadFirstPage(genreId);
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        if (firstPage) {
            fragmentView.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

            adapter.addAll(results);

            if (presenter.page <= presenter.totalPages) {
                adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }
        } else {
            adapter.removeLoadingFooter();
            presenter.isLoading = false;
            adapter.addAll(results);

            if (presenter.page != presenter.totalPages) {
                adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }
        }
    }

    @Override
    public void showError(int mode) {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }
}