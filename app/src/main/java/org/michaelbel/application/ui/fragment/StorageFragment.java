package org.michaelbel.application.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.ui.SettingsActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.cell.EmptyCell;
import org.michaelbel.application.ui.view.cell.TextCell;
import org.michaelbel.application.ui.view.cell.TextDetailCell;
import org.michaelbel.application.ui.view.movie.MovieViewList;
import org.michaelbel.application.ui.view.widget.IndicatorView;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.ui.view.widget.ViewPagerAdapter;
import org.michaelbel.application.util.ScreenUtils;
import org.michaelbel.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

@SuppressWarnings("all")
public class StorageFragment extends Fragment {

    private SettingsActivity activity;
    private ViewPagerAdapter viewPagerAdapter;

    private ViewPager viewPager;
    private IndicatorView indicatorView;
    private StorageUsageLayout storageUsageLayout;
    private CachedMoviesLayout cachedMoviesLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (SettingsActivity) getActivity();

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.toolbarTextView.setText(R.string.StorageUsage);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        storageUsageLayout = new StorageUsageLayout(activity);
        cachedMoviesLayout = new CachedMoviesLayout(activity);

        viewPagerAdapter = new ViewPagerAdapter(activity);
        viewPagerAdapter.addLayout(storageUsageLayout);
        viewPagerAdapter.addLayout(cachedMoviesLayout);

        viewPager = new ViewPager(activity);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(viewPager);

        indicatorView = new IndicatorView(activity);
        indicatorView.setViewPager(viewPager);
        indicatorView.setIndicatorDrawable(R.drawable.indicator_circle);
        indicatorView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0, 0, 24));
        fragmentView.addView(indicatorView);

        return fragmentView;
    }

    private long getCachedMoviesCount() {
        Realm realm = Realm.getDefaultInstance();
        long moviesCount = realm.where(Movie.class).count();
        return moviesCount;
    }

    private long getCachedImagesCount() {
        return 1020;
    }

    private class StorageUsageLayout extends FrameLayout {

        private int rowCount;
        private int keepMediaRow;
        private int emptyRow1;
        private int cachedImagesRow;
        private int cachingMoviesRow;
        private int emptyRow2;

        private int[] keepMedia = new int[] {
                R.string.KeepFewDays,
                R.string.KeepOneWeek,
                R.string.KeepOneMonth,
                R.string.KeepForever
        };

        private RecyclerListView recyclerView;

        public StorageUsageLayout(@NonNull Context context) {
            super(context);

            rowCount = 0;
            keepMediaRow = rowCount++;
            emptyRow1 = rowCount++;
            cachingMoviesRow = rowCount++;
            cachedImagesRow = rowCount++;
            emptyRow2 = rowCount++;

            recyclerView = new RecyclerListView(activity);
            recyclerView.setAdapter(new ListAdapter());
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
            recyclerView.setOnItemClickListener((view, position) -> {
                if (position == keepMediaRow) {
                    BottomSheet.Builder builder = new BottomSheet.Builder(activity);
                    builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
                    builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
                    builder.setCellHeight(ScreenUtils.dp(52));
                    builder.setItems(keepMedia, (dialogInterface, i) -> {
                        SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("keep_media", i);
                        editor.apply();

                        if (view instanceof TextDetailCell) {
                            ((TextDetailCell) view).setValue(keepMedia[i]);
                        }
                    });
                    builder.show();
                } else if (position == cachedImagesRow) {

                }
            });
            addView(recyclerView);
        }

        public class ListAdapter extends RecyclerView.Adapter {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
                View cell;

                if (type == 1) {
                    cell = new EmptyCell(activity);
                } else if (type == 2) {
                    cell = new TextCell(activity);
                } else {
                    cell = new TextDetailCell(activity);
                }

                return new Holder(cell);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                int type = getItemViewType(position);

                if (type == 1) {
                    EmptyCell cell = (EmptyCell) holder.itemView;
                    cell.setMode(EmptyCell.MODE_DEFAULT);
                    cell.setHeight(ScreenUtils.dp(12));
                } else if (type == 3) {
                    TextDetailCell cell = (TextDetailCell) holder.itemView;
                    cell.changeLayoutParams();

                    if (position == keepMediaRow) {
                        SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                        cell.setText(R.string.KeepMedia);
                        cell.setValue(keepMedia[prefs.getInt("keep_media", 3)]);
                    } else if (position == cachingMoviesRow) {
                        cell.setText(R.string.CachedMovies);
                        cell.setValue(getContext().getString(R.string.CountMovies, getCachedMoviesCount()));
                        cell.setDivider(true);
                    } else if (position == cachedImagesRow) {
                        cell.setText(R.string.CachedImages);
                        cell.setValue(getContext().getString(R.string.CountImages, getCachedImagesCount()));
                    }
                }
            }

            @Override
            public int getItemCount() {
                return rowCount;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == emptyRow1 || position == emptyRow2) {
                    return 1;
                } else if (position == 999) {
                    return 2;
                } else {
                    return 3;
                }
            }
        }
    }

    private class CachedMoviesLayout extends FrameLayout {

        private SearchMovieAdapter adapter;
        private List<Movie> cachedMoviesList = new ArrayList<>();

        private TextView headerView;
        private RecyclerView recyclerView;

        public CachedMoviesLayout(@NonNull Context context) {
            super(context);

            headerView = new TextView(context);
            headerView.setLines(1);
            headerView.setMaxLines(1);
            headerView.setSingleLine();
            headerView.setGravity(Gravity.CENTER_VERTICAL);
            headerView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            headerView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
            headerView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            headerView.setText(getContext().getString(R.string.CountMovies, getCachedMoviesCount()));
            headerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, 48, Gravity.START | Gravity.TOP, 16, 0, 16, 0));
            addView(headerView);

            adapter = new SearchMovieAdapter();

            recyclerView = new RecyclerListView(activity);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 48, 0, 48));
            addView(recyclerView);

            loadCachedMovies();
        }

        private void loadCachedMovies() {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Movie> results = realm.where(Movie.class).findAll();
            cachedMoviesList.addAll(results);
            adapter.notifyDataSetChanged();
        }

        public class SearchMovieAdapter extends RecyclerView.Adapter {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
                return new Holder(new MovieViewList(activity));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                Movie movie = cachedMoviesList.get(position);

                MovieViewList view = (MovieViewList) holder.itemView;
                view.setPoster(movie.posterPath)
                        .setTitle(movie.title)
                        .setYear(movie.releaseDate)
                        .setVoteAverage(movie.voteAverage)
                        .setDivider(position != cachedMoviesList.size() - 1);
            }

            @Override
            public int getItemCount() {
                return cachedMoviesList != null ? cachedMoviesList.size() : 0;
            }
        }
    }
}