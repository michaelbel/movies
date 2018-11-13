package org.michaelbel.moviemade.modules_beta.keywords;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
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
import org.michaelbel.moxy.android.MvpAppCompatFragment;
import org.michaelbel.tmdb.TmdbObject;
import org.michaelbel.tmdb.v3.json.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class KeywordMoviesFragment extends MvpAppCompatFragment implements ResultsMvp {

    private int keywordId;

    private KeywordActivity activity;
    private PaginationMoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    @InjectPresenter
    public KeywordMoviesPresenter presenter;

    public static KeywordMoviesFragment newInstance(int keywordId) {
        Bundle args = new Bundle();
        args.putInt("keywordId", keywordId);

        KeywordMoviesFragment fragment = new KeywordMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (KeywordActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.toolbarTitle.setOnClickListener(v -> {
            if (AndroidUtils.scrollToTop()) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (adapter.getList().isEmpty()) {
                presenter.loadFirstPage(keywordId);
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, Theme.accentColor()), PorterDuff.Mode.MULTIPLY);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        adapter = new PaginationMoviesAdapter();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS ? 1 : 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (view instanceof MovieViewListBig || view instanceof MovieViewPoster) {
                Movie movie = (Movie) adapter.getList().get(position);
                activity.startMovie(movie);
            }
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            //boolean favorite = presenter.isMovieFavorite(movie.id);
            boolean watchlist = presenter.isMovieWatchlist(movie.id);

            //int favoriteIcon = favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
            int watchlistIcon = watchlist ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_outline;
            //int favoriteText = favorite ? R.string.RemoveFromFavorites : R.string.AddToFavorites;
            int watchlistText = watchlist ? R.string.RemoveFromWatchList : R.string.AddToWatchlist;

            /*BottomSheet.Builder builder = new BottomSheet.Builder(activity);
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
                        presenter.loadNextPage(keywordId);
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

        keywordId = getArguments().getInt("keywordId");

        if (savedInstanceState == null) {
            presenter.loadFirstPage(keywordId);
        }
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
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
}