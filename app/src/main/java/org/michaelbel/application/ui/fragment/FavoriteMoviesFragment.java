package org.michaelbel.application.ui.fragment;

import android.app.Activity;
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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.sqlite.DatabaseHelper;
import org.michaelbel.application.ui.FavsActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.movie.MovieViewCard;
import org.michaelbel.application.ui.view.movie.MovieViewPoster;
import org.michaelbel.application.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("all")
public class FavoriteMoviesFragment extends Fragment {

    private FavsActivity activity;
    private MovieAdapter adapter;
    private GridLayoutManager layoutManager;
    private PaddingItemDecoration itemDecoration;
    private List<Movie> movieList = new ArrayList<>();

    private TextView emptyView;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (FavsActivity) getActivity();
        setHasOptionsMenu(true);

        fragmentView = new SwipeRefreshLayout(getContext());
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setOnRefreshListener(this :: loadMovies);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), Theme.primaryColor()));

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        emptyView = new TextView(activity);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
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
                Movie movie = movieList.get(position);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        layoutManager.setSpanCount(AndroidUtils.getColumns());
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
        fragmentView.setRefreshing(false);
    }

    private void onLoadSuccessful() {
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
            return AndroidUtils.viewType();
        }
    }
}