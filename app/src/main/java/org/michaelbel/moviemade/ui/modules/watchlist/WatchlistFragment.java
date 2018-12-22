package org.michaelbel.moviemade.ui.modules.watchlist;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.AccountService;
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.main.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.modules.main.adapter.OnMovieClickListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.utils.BuildUtil;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.IntentsKt;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class WatchlistFragment extends BaseFragment implements WatchlistContract.View, NetworkChangeListener, OnMovieClickListener {

    private int accountId;
    private WatchlistActivity activity;
    private MoviesAdapter adapter;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;
    private WatchlistContract.Presenter presenter;

    @Inject AccountService service;
    @Inject SharedPreferences sharedPreferences;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public static WatchlistFragment newInstance(int accountId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.ACCOUNT_ID, accountId);

        WatchlistFragment fragment = new WatchlistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (WatchlistActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
        Moviemade.get(activity).getComponent().injest(this);
        presenter = new WatchlistPresenter(this, service);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.getToolbar().setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 3)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getWatchlistMoviesNext(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""));
                }
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            accountId = args.getInt(IntentsKt.ACCOUNT_ID);
        }

        presenter.getWatchlistMovies(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""));
    }

    /*@Override
    public void onResume() {
        super.onResume();

        ((Moviemade) activity.getApplication()).rxBus2.toObserverable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object -> {
                if (object instanceof Events.DeleteMovieFromFavorite) {
                    int movieId = ((Events.DeleteMovieFromFavorite) object).movieId;
                    if (adapter != null) {
                        if (adapter.removeMovieById(movieId)) {
                            if (emptyView != null) {
                                emptyView.setVisibility(View.VISIBLE);
                                emptyView.setMode(EmptyViewMode.MODE_NO_MOVIES);
                            }
                        }
                    }
                }
            });
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
    }

    @OnClick(R.id.empty_view)
    void emptyViewClick(View v) {
        presenter.getWatchlistMovies(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""));
    }

    @Override
    public void setMovies(@NotNull List<Movie> movies) {
        connectionFailure = false;
        progressBar.setVisibility(View.GONE);
        adapter.addAll(movies);
    }

    @Override
    public void setError(int mode) {
        connectionFailure = false;
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);

        if (BuildUtil.INSTANCE.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key);
        }
    }

    public MoviesAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getWatchlistMovies(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""));
        }
    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
        // Fixme.
        activity.finish();
    }
}