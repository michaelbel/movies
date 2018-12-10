package org.michaelbel.moviemade.ui.modules.recommendations;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.base.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.modules.main.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.DeviceUtil;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("CheckResult")
@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class RecommendationsMoviesFragment extends MvpAppCompatFragment implements RecommendationsMvp, NetworkChangeListener {

    private Unbinder unbinder;
    private RecommendationsMoviesActivity activity;
    private MoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean connectionFailure = false;

    @Inject SharedPreferences sharedPreferences;
    @InjectPresenter public RecommendationsMoviesPresenter presenter;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) public RecyclerListView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (RecommendationsMoviesActivity) getActivity();
        Moviemade.getComponent().injest(this);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_similar_movies, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(DeviceUtil.INSTANCE.dp(activity, 1));

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter();
        gridLayoutManager = new GridLayoutManager(activity, spanCount, RecyclerView.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setPadding(DeviceUtil.INSTANCE.dp(activity, 2), 0, DeviceUtil.INSTANCE.dp(activity, 2), 0);
        recyclerView.setOnItemClickListener((v, position) -> {
            Movie movie = adapter.movies.get(position);
            activity.startMovie(movie);
            activity.finish();
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getRecommendationsNext(activity.movie.getId());
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshLayout();
    }

    @OnClick(R.id.empty_view)
    void emptyViewClick(View v) {
        presenter.getRecommendations(activity.movie.getId());
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
        unbinder.unbind();
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
        recyclerView.removeItemDecoration(itemDecoration);
        itemDecoration.setOffset(0);
        recyclerView.addItemDecoration(itemDecoration);
        gridLayoutManager.onRestoreInstanceState(state);
    }

    @Override
    public void onNetworkChanged() {
        if (connectionFailure && adapter.getItemCount() == 0) {
            presenter.getRecommendations(activity.movie.getId());
        }
    }
}