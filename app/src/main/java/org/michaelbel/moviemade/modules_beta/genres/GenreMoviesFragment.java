package org.michaelbel.moviemade.modules_beta.genres;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.tabs.TabLayout;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.eventbus.Events;
import org.michaelbel.moviemade.ui.modules.main.ResultsMvp;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.ui.adapters.PaginationMoviesAdapter;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.modules_beta.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.data.dao.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.functions.Consumer;

public class GenreMoviesFragment extends MvpAppCompatFragment implements ResultsMvp {

    private int genreId;

    private PaginationMoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

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
            ((GenreActivity) getActivity()).toolbarTitle.setOnClickListener(v -> {
                if (AndroidUtils.scrollToTop()) {
                    recyclerView.smoothScrollToPosition(0);
                }
            });
        } else if (getActivity() instanceof GenresActivity) {
            ((GenresActivity) getActivity()).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {}

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

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
            if (adapter.getList().isEmpty()) {
                presenter.loadFirstPage(genreId);
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        FrameLayout contentLayout = new FrameLayout(getContext());
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), Theme.accentColor()), PorterDuff.Mode.MULTIPLY);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(AndroidUtils.viewType() == 0 ? 0 : ScreenUtils.dp(1));

        adapter = new PaginationMoviesAdapter();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS ? 1 : 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            if (view instanceof MovieViewListBig || view instanceof MovieViewPoster) {
                if (getActivity() instanceof GenresActivity) {
                    ((GenresActivity) getActivity()).startMovie(movie);
                } else if (getActivity() instanceof GenreActivity) {
                    ((GenreActivity) getActivity()).startMovie(movie);
                }
            }
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            //boolean favorite = presenter.isMovieFavorite(movie.id);
            boolean watchlist = presenter.isMovieWatchlist(movie.getId());

            //int favoriteIcon = favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
            int watchlistIcon = watchlist ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_outline;
            //int favoriteText = favorite ? R.string.RemoveFromFavorites : R.string.AddToFavorites;
            int watchlistText = watchlist ? R.string.RemoveFromWatchList : R.string.AddToWatchlist;

            /*BottomSheet.Builder builder = new BottomSheet.Builder(getContext());
            builder.setCellHeight(ScreenUtils.dp(52));
            builder.setIconColorRes(Theme.iconActiveColor());
            builder.setItemTextColorRes(Theme.primaryTextColor());
            builder.setBackgroundColorRes(Theme.foregroundColor());
            builder.setItems(new int[] { favoriteText, watchlistText }, new int[] { favoriteIcon, watchlistIcon }, (dialog, i) -> {
                if (i == 0) {
                    presenter.movieFavoritesChange(movie);
                } else if (i == 1) {
                    presenter.movieWatchlistChange(movie);
                }
            });
            if (AndroidUtils.additionalOptions()) {
                builder.show();
            }*/
            return true;
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = gridLayoutManager.getItemCount();
                int visibleItemCount = gridLayoutManager.getChildCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

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

        if (savedInstanceState == null) {
            presenter.loadFirstPage(genreId);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();

        ((Moviemade) getActivity().getApplication()).eventBus().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof Events.MovieListRefreshLayout) {
                    refreshLayout();
                } else if (o instanceof Events.ChangeTheme) {
                    changeTheme();
                }
            }
        });
    }

    @Override
    public void showResults(List<Movie> results, boolean firstPage) {
        if (firstPage) {
            fragmentView.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

            adapter.addAll(results);

            if (presenter.page < presenter.totalPages) {
                //adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }
        } else {
            adapter.removeLoadingFooter();
            presenter.isLoading = false;
            adapter.addAll(results);

            if (presenter.page != presenter.totalPages) {
                //adapter.addLoadingFooter();
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

    private void refreshLayout() {
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS ? 1 : 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
            recyclerView.addItemDecoration(itemDecoration);
        } else if (AndroidUtils.viewType() == 1) {
            itemDecoration.setOffset(ScreenUtils.dp(1));
            recyclerView.addItemDecoration(itemDecoration);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }

    private void changeTheme() {
        //fragmentView.setBackgroundColor(ContextCompat.getColor(getContext(), Theme.backgroundColor()));
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
    }
}