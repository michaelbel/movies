package org.michaelbel.application.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.AppLoader;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.sqlite.DatabaseHelper;
import org.michaelbel.application.ui.FavsActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.application.ui.view.movie.MovieViewCard;
import org.michaelbel.application.ui.view.movie.MovieViewPoster;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AppUtils;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteMoviesFragment extends Fragment {

    private FavsActivity activity;
    private MovieAdapter adapter;
    private GridLayoutManager layoutManager;
    private PaddingItemDecoration itemDecoration;
    private List<Movie> movieList = new ArrayList<>();

    private TextView emptyView;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (FavsActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_favs, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        setHasOptionsMenu(true);

        emptyView = fragmentView.findViewById(R.id.empty_view);

        refreshLayout = fragmentView.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(Theme.accentColor());
        refreshLayout.setOnRefreshListener(this :: loadMovies);

        SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        int type = prefs.getInt("view_type", 0);

        adapter = new MovieAdapter();
        layoutManager = new GridLayoutManager(activity, getLayoutColumns());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        itemDecoration = new PaddingItemDecoration();
        if (type == 0) {
            itemDecoration.setOffset(ScreenUtils.dp(2));
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        if (type == 0) {
            recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        }
        recyclerView.setOnItemClickListener((view1, position) -> {
                Movie movie = movieList.get(position);
                activity.startMovie(movie.id, movie.title);
        });

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMovies();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem viewItem = menu.add(R.string.ChangeViewType);
        viewItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        int viewType = prefs.getInt("view_type", 0);

        if (viewType == 0) {
            viewItem.setIcon(R.drawable.ic_view_default);
        } else if (viewType == 1) {
            viewItem.setIcon(R.drawable.ic_view_compat);
        }

        viewItem.setOnMenuItemClickListener(item -> {
            int newType = prefs.getInt("view_type", 0);

            if (newType == 0) {
                viewItem.setIcon(R.drawable.ic_view_compat);
                newType = 1;
            } else if (newType == 1) {
                viewItem.setIcon(R.drawable.ic_view_default);
                newType = 0;
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("view_type", newType);
            editor.apply();

            refreshLayout();
            return true;
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutManager.setSpanCount(AppUtils.getColumns());
    }

    private void loadMovies() {
        if (!movieList.isEmpty()) {
            movieList.clear();
        }

        DatabaseHelper database = DatabaseHelper.getInstance(activity);
        if (database.getMoviesList().isEmpty()) {
            onLoadError();
        } else {
            movieList.addAll(database.getMoviesList());
            Collections.reverse(movieList);
            onLoadSuccessful();
        }
        adapter.notifyDataSetChanged();
        database.close();
    }

    private void onLoadError() {
        emptyView.setText(R.string.NoMovies);
        refreshLayout.setRefreshing(false);
    }

    private void onLoadSuccessful() {
        refreshLayout.setRefreshing(false);
    }

    private void refreshLayout() {
        SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        int type = prefs.getInt("view_type", 0);

        Parcelable state = layoutManager.onSaveInstanceState();
        layoutManager = new GridLayoutManager(activity, getLayoutColumns());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (type == 0) {
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
        SharedPreferences prefs = AppLoader.AppContext.getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        int viewType = prefs.getInt("view_type", 0);

        if (viewType == 0) {
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
            Movie movie = movieList.get(position);

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
            return movieList != null ? movieList.size() : 0;
        }

        @Override
        public int getItemViewType(int position) {
            SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
            return prefs.getInt("view_type", 0);
        }
    }
}