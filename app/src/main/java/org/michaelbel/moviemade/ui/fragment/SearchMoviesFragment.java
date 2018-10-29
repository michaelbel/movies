package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.presenter.SearchMoviesPresenter;
import org.michaelbel.moviemade.mvp.view.MvpSearchView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.activity.SearchActivity;
import org.michaelbel.moviemade.ui_old.adapter.pagination.PaginationMoviesAdapter;
import org.michaelbel.moviemade.ui_old.view.EmptyView;
import org.michaelbel.moviemade.ui_old.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.List;

public class SearchMoviesFragment extends MvpAppCompatFragment implements MvpSearchView {

    private SearchActivity activity;
    private PaginationMoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;

    @InjectPresenter
    public SearchMoviesPresenter presenter;

    public static SearchMoviesFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);

        SearchMoviesFragment fragment = new SearchMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SearchActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_movies, container, false);

        emptyView = view.findViewById(R.id.empty_view);
        emptyView.setMode(EmptyViewMode.MODE_NO_RESULTS);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        PaddingItemDecoration itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        adapter = new PaginationMoviesAdapter();
        gridLayoutManager = new GridLayoutManager(activity, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setOnItemClickListener((v, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            activity.startMovie(movie);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = gridLayoutManager.getItemCount();
                int visibleItemCount = gridLayoutManager.getChildCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                if (!presenter.isLoading && !presenter.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0/* && totalItemCount >= presenter.totalPages*/) {
                        presenter.isLoading = true;
                        presenter.page++;
                        presenter.loadNextPage();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        String readyQuery = getArguments().getString("query");

        if (readyQuery == null) {
            activity.searchEditText.setSelection(activity.searchEditText.getText().length());
        } else {
            activity.searchEditText.setText(readyQuery);
            activity.searchEditText.setSelection(activity.searchEditText.getText().length());

            AndroidUtils.hideKeyboard(activity.searchEditText);
            presenter.search(readyQuery);
        }
    }

    @Override
    public void searchStart() {
        adapter.getList().clear();
        adapter.notifyDataSetChanged();

        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
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
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
    }

    public boolean empty() {
        return adapter.getList().isEmpty();
    }
}