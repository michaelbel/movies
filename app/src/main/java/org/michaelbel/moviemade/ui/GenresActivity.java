package org.michaelbel.moviemade.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.databinding.ActivityGenresBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.mvp.presenter.GenresPresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.ui.fragment.GenreMoviesFragment;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GenresActivity extends BaseActivity implements MvpResultsView {

    private ArrayList<Genre> genres;
    private FragmentsPagerAdapter adapter;

    public ActivityGenresBinding binding;

    private EmptyView emptyView;

    @InjectPresenter
    public GenresPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_genres);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        genres = getIntent().getParcelableArrayListExtra("list");
        if (genres != null) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        binding.toolbar.setNavigationIcon(genres != null ? R.drawable.ic_arrow_back : R.drawable.ic_menu);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (genres != null) {
                finish();
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.toolbarTitle.setText(R.string.LoadingGenres);
        binding.navigationView.setOnNavigationItemSelectedListener((view, position) -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);

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
        binding.contentLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.backgroundColor()));

        emptyView = new EmptyView(this);
        emptyView.setVisibility(View.GONE);
        binding.contentLayout.addView(emptyView);

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
        binding.tabLayout.setVisibility(View.INVISIBLE);

        if (genres != null) {
            showFromExtra();
        } else {
            presenter.loadGenres();
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showResults(List<TmdbObject> results) {
        for (TmdbObject genre : results) {
            adapter.addFragment(GenreMoviesFragment.newInstance(((Genre) genre).id), ((Genre) genre).name);
        }

        adapter.notifyDataSetChanged();

        binding.toolbarTitle.setVisibility(View.GONE);
        binding.tabLayout.setVisibility(View.VISIBLE);
        binding.toolbarLayout.removeView(binding.progressBar);
    }

    @Override
    public void showError(int mode) {
        binding.toolbarLayout.removeView(binding.progressBar);

        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);

        binding.toolbarTitle.setText(R.string.Genres);
    }

    private void showFromExtra() {
        for (TmdbObject genre : genres) {
            adapter.addFragment(GenreMoviesFragment.newInstance(((Genre) genre).id), ((Genre) genre).name);
        }

        adapter.notifyDataSetChanged();

        binding.toolbarTitle.setVisibility(View.GONE);
        binding.tabLayout.setVisibility(View.VISIBLE);
        binding.toolbarLayout.removeView(binding.progressBar);
    }
}