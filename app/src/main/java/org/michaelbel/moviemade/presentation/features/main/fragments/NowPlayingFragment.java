package org.michaelbel.moviemade.presentation.features.main.fragments;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.core.entity.Movie;
import org.michaelbel.moviemade.core.utils.BuildUtil;
import org.michaelbel.moviemade.core.utils.DeviceUtil;
import org.michaelbel.moviemade.presentation.App;
import org.michaelbel.moviemade.presentation.base.BaseFragment;
import org.michaelbel.moviemade.presentation.common.EmptyView;
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration;
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver;
import org.michaelbel.moviemade.presentation.features.main.MainActivity;
import org.michaelbel.moviemade.presentation.features.main.MainContract;
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class NowPlayingFragment extends BaseFragment
        implements MainContract.View, NetworkChangeReceiver.Listener, MoviesAdapter.Listener {

    private MainActivity activity;
    private MoviesAdapter adapter;

    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @Inject MainContract.Presenter presenter;
    @Inject SharedPreferences prefs;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public MainContract.Presenter getPresenter() {
        return presenter;
    }

    public MoviesAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();

        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));

        ((App) activity.getApplication()).createFragmentComponent().inject(this);
        presenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 3)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && adapter.getItemCount() != 0) {
                    presenter.getNowPlayingNext();
                }
            }
        });

        emptyView.setOnClickListener(v -> presenter.getTopRated());

        presenter.getNowPlaying();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.destroy();
    }

    @Override
    public void setContent(@NotNull List<Movie> movies) {
        connectionFailure = false;
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        adapter.addMovies(movies);
    }

    @Override
    public void setError(int mode) {
        connectionFailure = false;
        progressBar.setVisibility(View.GONE);

        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);

        if (BuildUtil.INSTANCE.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key);
        }
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getNowPlaying();
        }
    }

    @Override
    public void setLoading() {

    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
    }
}