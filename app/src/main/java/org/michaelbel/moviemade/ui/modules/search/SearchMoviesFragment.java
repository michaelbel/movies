package org.michaelbel.moviemade.ui.modules.search;

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
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.utils.BuildUtil;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SearchMoviesFragment extends BaseFragment implements SearchContract.View, OnMovieClickListener {

    private MoviesAdapter adapter;
    private SearchActivity activity;

    @Inject SearchContract.Presenter presenter;

    @BindView(R.id.empty_view) EmptyView emptyView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    static SearchMoviesFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(IntentsKt.QUERY, query);

        SearchMoviesFragment fragment = new SearchMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SearchActivity) getActivity();

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

        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 3)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadNextResults();
                }
            }
        });

        progressBar.setVisibility(View.INVISIBLE);

        emptyView.setMode(EmptyViewMode.MODE_NO_RESULTS);

        String readyQuery = getArguments() != null ? getArguments().getString(IntentsKt.QUERY) : null;

        if (readyQuery == null) {
            activity.getSearchEditText().setSelection(Objects.requireNonNull(activity.getSearchEditText().getText()).length());
        } else {
            activity.getSearchEditText().setText(readyQuery);
            activity.getSearchEditText().setSelection(Objects.requireNonNull(activity.getSearchEditText().getText()).length());

            activity.hideKeyboard(activity.getSearchEditText());
            presenter.search(readyQuery);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void searchStart() {
        adapter.getMovies().clear();
        adapter.notifyDataSetChanged();

        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovies(@NotNull List<Movie> movies) {
        progressBar.setVisibility(View.GONE);
        adapter.addMovies(movies);
    }

    @Override
    public void setError(int mode) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);

        if (BuildUtil.INSTANCE.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key);
        }
    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
    }
}