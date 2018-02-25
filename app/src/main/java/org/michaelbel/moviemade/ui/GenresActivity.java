package org.michaelbel.moviemade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.mvp.presenter.GenresPresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.ui.fragment.GenreMoviesFragment;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.NavigationView;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GenresActivity extends BaseActivity implements MvpResultsView {

    private ArrayList<Genre> genres;
    private FragmentsPagerAdapter adapter;

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public ProgressBar progressBar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public FrameLayout contentLayout;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    public FrameLayout toolbarLayout;

    private EmptyView emptyView;

    @InjectPresenter
    public GenresPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        contentLayout = findViewById(R.id.content_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        navigationView = findViewById(R.id.navigation_view);
        progressBar = findViewById(R.id.progress_bar);

        genres = getIntent().getParcelableArrayListExtra("list");
        if (genres != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        toolbar.setNavigationIcon(genres != null ? R.drawable.ic_arrow_back : R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            if (genres != null) {
                finish();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        toolbarTitle.setText(R.string.LoadingGenres);
        navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            if (position == 2) {
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
            } else if (position == 3) {
                // GenresActivity
            } else if (position == 4) {
                startActivity(new Intent(this, PopularPeopleActivity.class));
                this.finish();
            } else if (position == 6) {
                startActivity(new Intent(this, WatchlistActivity.class));
            } else if (position == 7) {
                startActivity(new Intent(this, FavoriteActivity.class));
            } else if (position == 9) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else if (position == 10) {
                startActivity(new Intent(this, AboutActivity.class));
            }
        });

        contentLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.backgroundColor()));

        emptyView = new EmptyView(this);
        emptyView.setVisibility(View.GONE);
        contentLayout.addView(emptyView);

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());


        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
        tabLayout.setVisibility(View.INVISIBLE);

        if (genres != null) {
            showFromExtra();
        } else {
            presenter.loadGenres();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        for (TmdbObject genre : results) {
            adapter.addFragment(GenreMoviesFragment.newInstance(((Genre) genre).id), ((Genre) genre).name);
        }

        adapter.notifyDataSetChanged();

        toolbarTitle.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        toolbarLayout.removeView(progressBar);
    }

    @Override
    public void showError(int mode) {
        toolbarLayout.removeView(progressBar);

        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);

        toolbarTitle.setText(R.string.Genres);
    }

    private void showFromExtra() {
        for (TmdbObject genre : genres) {
            adapter.addFragment(GenreMoviesFragment.newInstance(((Genre) genre).id), ((Genre) genre).name);
        }

        adapter.notifyDataSetChanged();

        toolbarTitle.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        toolbarLayout.removeView(progressBar);
    }
}