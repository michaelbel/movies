package org.michaelbel.moviemade.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.eventbus.Events;
import org.michaelbel.moviemade.mvp.presenter.ListMoviesPresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.activity.MainActivity;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui_old.adapter.pagination.PaginationMoviesAdapter;
import org.michaelbel.moviemade.ui_old.view.widget.PaddingItemDecoration;
import org.michaelbel.moxy.android.MvpAppCompatFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("all")
public class UpcomingFragment extends MvpAppCompatFragment implements MvpResultsView {

    private MainActivity activity;
    public PaginationMoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    public RecyclerListView recyclerView;

    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @InjectPresenter
    public ListMoviesPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        activity.registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyView.setOnClickListener(v -> presenter.loadUpcomingMovies());

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(Extensions.dp(activity, 1));

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new PaginationMoviesAdapter();
        gridLayoutManager = new GridLayoutManager(activity, spanCount);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setPadding(Extensions.dp(activity, 2), 0, Extensions.dp(activity, 2), 0);
        recyclerView.setOnItemClickListener((v, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            activity.startMovie(movie);
        });
        /*recyclerView.setOnItemLongClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            boolean favorite = presenter.isMovieFavorite(movie.id);
            boolean watchlist = presenter.isMovieWatchlist(movie.id);

            int favoriteIcon = favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
            int watchlistIcon = watchlist ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_outline;
            int favoriteText = favorite ? R.string.RemoveFromFavorites : R.string.AddToFavorites;
            int watchlistText = watchlist ? R.string.RemoveFromWatchList : R.string.AddToWatchlist;

            BottomSheet.Builder builder = new BottomSheet.Builder(getContext());
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
            }
            return true;
        });*/
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = gridLayoutManager.getItemCount();
                int visibleItemCount = gridLayoutManager.getChildCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                if (!presenter.isLoading && !presenter.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        presenter.isLoading = true;
                        presenter.page++;
                        loadNextPage();
                    }
                }
            }
        });

        presenter.loadUpcomingMovies();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        Moviemade app = ((Moviemade) activity.getApplication());
        app.eventBus().toObservable().subscribe(o -> {
            if (o instanceof Events.MovieListRefreshLayout) {
                refreshLayout();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        if (firstPage) {
            progressBar.setVisibility(View.GONE);
            adapter.addAll(results);

            if (presenter.page < presenter.totalPages) {
                // show loading
            } else {
                presenter.isLastPage = true;
            }
        } else {
            // hide loading
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
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }

    private void loadNextPage() {
        presenter.loadUpcomingNextMovies();
    }

    private void refreshLayout() {
        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        itemDecoration.setOffset(0);
        recyclerView.addItemDecoration(itemDecoration);
        gridLayoutManager.onRestoreInstanceState(state);
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

            if (networkInfo != null && networkInfo.isConnected()) {
                if (adapter.isEmpty()) {
                    presenter.loadUpcomingMovies();
                }
            }
        }
    }
}