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

import org.michaelbel.moviemade.FavoriteActivity;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.adapter.Holder;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewCard;
import org.michaelbel.moviemade.ui.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteMoviesFragment extends Fragment {

    private FavoriteActivity activity;
    private MovieAdapter adapter;
    private GridLayoutManager layoutManager;
    private PaddingItemDecoration itemDecoration;
    private List<Movie> movies = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        activity = (FavoriteActivity) getActivity();

        activity.binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        fragmentView = new SwipeRefreshLayout(getContext());
        fragmentView.setRefreshing(false);
        fragmentView.setOnRefreshListener(this :: loadMovies);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), Theme.primaryColor()));

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
        layoutManager = new GridLayoutManager(activity, getLayoutColumns());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(ScreenUtils.dp(2));
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == 0) {
            recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        }
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
                Movie movie = movies.get(position);
                //activity.startMovie(movie);
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
        layoutManager.setSpanCount(AndroidUtils.getColumns());
    }

    private void loadMovies() {
        if (!movies.isEmpty()) {
            movies.clear();
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Movie> results = realm.where(Movie.class).equalTo("favorite", true).findAll();
        if (results.isEmpty()) {
            onLoadError();
        } else {
            movies.addAll(results);
            Collections.reverse(movies);
            onLoadSuccessful();
        }

        adapter.notifyDataSetChanged();
        //realm.close();

        /*DatabaseHelper database = DatabaseHelper.getInstance(activity);
        if (database.getMoviesList().isEmpty()) {
            onLoadError();
        } else {
            movies.addAll(database.getMoviesList());
            Collections.reverse(movies);
            onLoadSuccessful();
        }
        adapter.notifyDataSetChanged();
        database.close();*/
    }

    private void onLoadError() {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.INVISIBLE);
        emptyView.setMode(EmptyView.MODE_NO_MOVIES);
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }

    private void refreshLayout() {
        Parcelable state = layoutManager.onSaveInstanceState();
        layoutManager = new GridLayoutManager(activity, getLayoutColumns());
        recyclerView.setLayoutManager(layoutManager);
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
        layoutManager.onRestoreInstanceState(state);
    }

    public int getLayoutColumns() {
        if (AndroidUtils.viewType() == 0) {
            return ScreenUtils.isPortrait() ? 3 : 5;
        } else {
            return ScreenUtils.isPortrait() ? 2 : 3;
        }
    }

    public class MovieAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View view;

            if (type == 0) {
                view = new MovieViewCard(activity);
            } else {
                view = new MovieViewPoster(activity);
            }

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Movie movie = movies.get(position);

            if (getItemViewType(position) == 0) {
                MovieViewCard view = (MovieViewCard) holder.itemView;
                view.getPosterImage().setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 150));
                view.setPoster(movie.posterPath)
                    .setTitle(movie.title)
                    .setYear(movie.releaseDate);
            } else {
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