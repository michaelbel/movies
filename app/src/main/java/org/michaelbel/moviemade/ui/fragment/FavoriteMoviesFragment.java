package org.michaelbel.moviemade.ui.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.michaelbel.moviemade.ui.FavoriteActivity;
import org.michaelbel.moviemade.R;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.ui.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.core.widget.RecyclerListView;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteMoviesFragment extends Fragment {

    private MovieAdapter adapter;
    private FavoriteActivity activity;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;
    private List<MovieRealm> movies = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (FavoriteActivity) getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem viewItem = menu.add(R.string.ChangeViewType);
        viewItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        viewItem.setIcon(R.drawable.ic_view_default);

        viewItem.setOnMenuItemClickListener(item -> {
            int newType = AndroidUtils.viewType();

            if (newType == 0) {
                newType = 1;
            } else if (newType == 1) {
                newType = 0;
            }

            SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("view_type", newType);
            editor.apply();

            refreshLayout();
            return true;
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (AndroidUtils.scrollToTop()) {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setOnRefreshListener(this :: loadMovies);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.primaryColor()));

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        adapter = new MovieAdapter();
        gridLayoutManager = new GridLayoutManager(activity, AndroidUtils.getSpanForMovies());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        //if (AndroidUtils.viewType() == 0) {
        //    recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        //}
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
                MovieRealm movie = movies.get(position);
                activity.startMovie(movie);
        });
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMovies();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(AndroidUtils.getColumns());
    }

    private void loadMovies() {
        if (!movies.isEmpty()) {
            movies.clear();
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<MovieRealm> results = realm.where(MovieRealm.class).equalTo("favorite", true).findAll();
        if (results.isEmpty()) {
            onLoadError();
        } else {
            movies.addAll(results);
            Collections.reverse(movies);
            onLoadSuccessful();
        }

        adapter.notifyDataSetChanged();
    }

    private void onLoadError() {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.INVISIBLE);
        emptyView.setMode(EmptyViewMode.MODE_NO_MOVIES);
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }

    private void refreshLayout() {
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, getLayoutColumns());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(ScreenUtils.dp(2));
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setPadding(0, 0, 0, 0);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }

    public int getLayoutColumns() {
        if (AndroidUtils.viewType() == 0) {
            return ScreenUtils.isPortrait() ? 3 : 5;
        } else {
            return ScreenUtils.isPortrait() ? 2 : 3;
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View view = null;

            if (type == 0) {
                view = new MovieViewListBig(parent.getContext());
            } else if (type == 1) {
                view = new MovieViewPoster(parent.getContext());
            }

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            MovieRealm movie = movies.get(position);

            if (type == 0) {
                MovieViewListBig view = (MovieViewListBig) holder.itemView;
                view.setPoster(movie.posterPath)
                    .setTitle(movie.title)
                    .setRating(String.valueOf(movie.voteAverage))
                    .setVoteCount(String.valueOf(movie.voteCount))
                    .setReleaseDate(movie.releaseDate)
                    .setOverview(movie.overview)
                    .setDivider(position != movies.size() - 1);
            } else if (type == 1) {
                MovieViewPoster view = (MovieViewPoster) holder.itemView;
                view.setPoster(movie.posterPath);
            }
        }

        @Override
        public int getItemCount() {
            return movies != null ? movies.size() : 0;
        }

        @Override
        public int getItemViewType(int position) {
            return AndroidUtils.viewType();
        }
    }
}