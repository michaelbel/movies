package org.michaelbel.moviemade.ui.modules.keywords.fragment;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.eventbus.Events;
import org.michaelbel.moviemade.data.service.KeywordsService;
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordContract;
import org.michaelbel.moviemade.ui.modules.keywords.KeywordPresenter;
import org.michaelbel.moviemade.ui.modules.keywords.activity.KeywordActivity;
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

public class KeywordFragment extends BaseFragment implements KeywordContract.View, NetworkChangeListener, OnMovieClickListener {

    private int keywordId;
    private KeywordActivity activity;
    private MoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;
    private KeywordContract.Presenter presenter;

    @Inject KeywordsService service;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public static KeywordFragment newInstance(int keywordId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.KEYWORD_ID, keywordId);

        KeywordFragment fragment = new KeywordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public KeywordContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (KeywordActivity) getActivity();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
        Moviemade.get(activity).getComponent().injest(this);
        presenter = new KeywordPresenter(this, service);
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
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        GridSpacingItemDecoration spacingDecoration = new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 3));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(spacingDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getMoviesNext(keywordId);
                }
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            keywordId = args.getInt(IntentsKt.KEYWORD_ID);
        }

        presenter.getMovies(keywordId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshLayout();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();

        ((Moviemade) activity.getApplication()).eventBus().toObservable().subscribe(o -> {
            if (o instanceof Events.MovieListRefreshLayout) {
                refreshLayout();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
    }

    // TODO add method
    @OnClick(R.id.empty_view)
    void emptyViewClick(View v) {
        presenter.getMovies(keywordId);
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
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getMovies(keywordId);
        }
    }

    private void refreshLayout() {
        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.onRestoreInstanceState(state);
    }

    public MoviesAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
    }
}