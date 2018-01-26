package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.mvp.presenter.CollectionPresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.CollectionActivity;
import org.michaelbel.moviemade.ui.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.ui.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;
import org.michaelbel.moviemade.util.ScreenUtils;

import java.util.List;

public class CollectionFragment extends MvpAppCompatFragment implements MvpResultsView {

    private Collection collection;

    private MoviesAdapter adapter;
    private CollectionActivity activity;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;
    private TextView overviewText;

    @InjectPresenter
    public CollectionPresenter presenter;

    public static CollectionFragment newInstance(Collection collection) {
        Bundle args = new Bundle();
        args.putParcelable("collection", collection);

        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CollectionActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.binding.toolbarTitle.setOnClickListener(v -> {
            if (AndroidUtils.scrollToTop()) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (adapter.getMovies().isEmpty()) {
                presenter.loadMovies(collection.id);
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        //ScrollView scrollView = new ScrollView(activity);
        //scrollView.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        //fragmentView.addView(scrollView);

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        //LinearLayout collectionViewLayout = new LinearLayout(activity);
        //collectionViewLayout.setOrientation(LinearLayout.VERTICAL);
        //collectionViewLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        //contentLayout.addView(collectionViewLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        // Content

        //overviewText = new TextView(activity);
        //overviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        //collectionViewLayout.addView(overviewText);

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(AndroidUtils.viewType() == 0 ? 0 : ScreenUtils.dp(1));

        adapter = new MoviesAdapter();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.getSpanForMovies());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (view instanceof MovieViewListBig || view instanceof MovieViewPoster) {
                Movie movie = (Movie) adapter.getMovies().get(position);
                activity.startMovie(movie);
            }
        });
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        collection = getArguments().getParcelable("collection");

        //overviewText.setText(collection.overview);
        /*Picasso.with(getContext())
                .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.backdropSize(), collection.backdropPath))
                .placeholder(R.drawable.movie_placeholder_old)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(activity.binding.backdropView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //progressBar.setVisibility(GONE);
                    }

                    @Override
                    public void onError() {
                        //progressBar.setVisibility(GONE);
                    }
                });*/

        //activity.binding.collapsingLayout.setTitle(collection.name);

        if (savedInstanceState == null) {
            presenter.loadMovies(collection.id);
        }
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        adapter.addMovies(results);
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int mode) {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }
}