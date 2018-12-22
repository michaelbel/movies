package org.michaelbel.moviemade.ui.modules.recommendations;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.service.MoviesService;
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.main.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.modules.main.adapter.OnMovieClickListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.ui.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class RcmdMoviesFragment extends BaseFragment implements RcmdContract.View, NetworkChangeListener, OnMovieClickListener {

    private int movieId;
    private RcmdMoviesActivity activity;
    private MoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;
    private RcmdContract.Presenter presenter;

    @Inject MoviesService service;
    @Inject SharedPreferences sharedPreferences;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) public RecyclerView recyclerView;

    public RcmdContract.Presenter getPresenter() {
        return presenter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static RcmdMoviesFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.MOVIE_ID, movieId);

        RcmdMoviesFragment fragment = new RcmdMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (RcmdMoviesActivity) getActivity();
        Moviemade.get(activity).getComponent().injest(this);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
        presenter = new RcmdPresenter(this, service);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.toolbar.setOnClickListener(v -> getRecyclerView().smoothScrollToPosition(0));

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter(this);
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        GridSpacingItemDecoration spacingDecoration = new GridSpacingItemDecoration(2, DeviceUtil.INSTANCE.dp(activity, 3));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(spacingDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getRcmdMoviesNext(movieId);
                }
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            movieId = args.getInt(IntentsKt.MOVIE_ID);
        }

        presenter.getRcmdMovies(movieId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshLayout();
    }

    // fixme del
    @OnClick(R.id.empty_view)
    void emptyViewClick(View v) {
        presenter.getRcmdMoviesNext(movieId);
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

        // noinspection ConstantConditions, StringEquality
        if (BuildConfig.TMDB_API_KEY == "null") {
            emptyView.setValue(R.string.error_empty_api_key);
        }
    }

    public MoviesAdapter getAdapter() {
        return adapter;
    }

    private void refreshLayout() {
        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.onRestoreInstanceState(state);
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getRcmdMovies(movieId);
        }
    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
    }
}