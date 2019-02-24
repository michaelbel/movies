package org.michaelbel.moviemade.presentation.features.similar;

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
import org.michaelbel.moviemade.core.utils.DeviceUtil;
import org.michaelbel.moviemade.core.utils.IntentsKt;
import org.michaelbel.moviemade.core.utils.ViewUtil;
import org.michaelbel.moviemade.presentation.App;
import org.michaelbel.moviemade.presentation.base.BaseFragment;
import org.michaelbel.moviemade.presentation.common.ErrorView;
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration;
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeListener;
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver;
import org.michaelbel.moviemade.presentation.features.main.MoviesAdapter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SimilarMoviesFragment extends BaseFragment implements
        SimilarContract.View, NetworkChangeListener, MoviesAdapter.Listener, ErrorView.ErrorListener, SwipeRefreshLayout.OnRefreshListener {

    private int movieId;
    private SimilarMoviesActivity activity;
    private MoviesAdapter adapter;

    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @Inject
    SimilarContract.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    private ErrorView errorView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static SimilarMoviesFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt(IntentsKt.MOVIE_ID, movieId);

        SimilarMoviesFragment fragment = new SimilarMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SimilarMoviesActivity) getActivity();

        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));

        ((App) activity.getApplication()).createFragmentComponent().inject(this);
        presenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lce, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.getToolbar().setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter(this);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(ViewUtil.INSTANCE.getAttrColor(activity, android.R.attr.colorAccent));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ViewUtil.INSTANCE.getAttrColor(activity, android.R.attr.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(VISIBLE);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 3)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // the app crashes if scrolling while error view showing.
                // the app crashes if scrolling while playing progress bar.
                if (!recyclerView.canScrollVertically(1) && adapter.getItemCount() != 0 && progressBar.getVisibility() == GONE) {
                    presenter.getSimilarMoviesNext(movieId);
                }
            }
        });

        errorView = view.findViewById(R.id.error_view);
        errorView.setErrorListener(this);

        movieId = getArguments() != null ? getArguments().getInt(IntentsKt.MOVIE_ID) : 0;
        presenter.getSimilarMovies(movieId);
    }

    @Override
    public void setMovies(@NotNull List<Movie> movies) {
        connectionFailure = false;
        progressBar.setVisibility(GONE);
        swipeRefreshLayout.setRefreshing(false);
        errorView.setVisibility(GONE);
        adapter.addMovies(movies);
    }

    @Override
    public void setError(@NonNull Throwable error, int code) {
        connectionFailure = true;
        progressBar.setVisibility(GONE);
        swipeRefreshLayout.setRefreshing(false);
        errorView.setVisibility(VISIBLE);
        errorView.setError(error, code);

        Timber.d(error, getClass().getSimpleName());
    }

    @Override
    public void onReloadData() {
        errorView.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
        presenter.getSimilarMovies(movieId);
    }

    @Override
    public void onRefresh() {
        if (progressBar.getVisibility() == GONE) {
            if (adapter.getItemCount() == 0) {
                errorView.setVisibility(GONE);
                presenter.getSimilarMovies(movieId);
            } else {
                adapter.getMovies().clear();
                adapter.notifyDataSetChanged();
                presenter.getSimilarMovies(movieId);
            }
        }
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getSimilarMovies(movieId);
        }
    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.destroy();
    }
}