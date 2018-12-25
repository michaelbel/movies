package org.michaelbel.moviemade.ui.modules.favorites;

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
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.main.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.modules.main.adapter.OnMovieClickListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
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

public class FavoritesFragment extends BaseFragment implements FavoritesContract.View, NetworkChangeListener, OnMovieClickListener {

    private int accountId;
    private MoviesAdapter adapter;
    private FavoriteActivity activity;
    private GridLayoutManager gridLayoutManager;

    // Fixme.
    //private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @Inject FavoritesContract.Presenter presenter;
    @Inject SharedPreferences sharedPreferences;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    static FavoritesFragment newInstance(int accountId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.ACCOUNT_ID, accountId);

        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (FavoriteActivity) getActivity();

        //networkChangeReceiver = new NetworkChangeReceiver(this);
        //activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));

        if (activity != null) {
            Moviemade.get(activity).getFragmentComponent().inject(this);
        }
        presenter.setView(this);
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
        emptyView.setOnClickListener(v -> presenter.getFavoriteMovies(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, "")));

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter(this);
        gridLayoutManager = new GridLayoutManager(activity, spanCount);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 3)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && adapter.getItemCount() != 0) {
                    presenter.getFavoriteMoviesNext(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""));
                }
            }
        });

        accountId = getArguments() != null ? getArguments().getInt(IntentsKt.ACCOUNT_ID) : 0;
        presenter.getFavoriteMovies(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""));
    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
        // FIXME.
        activity.finish();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMovies(@NotNull List<Movie> movies) {
        connectionFailure = false;
        adapter.addMovies(movies);
        hideLoading();
        emptyView.setVisibility(View.GONE);
    }

    @SuppressWarnings({"ConstantConditions", "StringEquality"})
    @Override
    public void setError(int mode) {
        connectionFailure = false;
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
        hideLoading();

        if (BuildUtil.INSTANCE.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key);
        }
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getFavoriteMovies(accountId, sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, ""));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
    }
}