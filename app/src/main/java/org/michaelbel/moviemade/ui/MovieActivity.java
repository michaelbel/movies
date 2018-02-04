package org.michaelbel.moviemade.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.databinding.ActivityMovieBinding;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.fragment.ListMoviesFragment;
import org.michaelbel.moviemade.ui.fragment.MovieCastsFragment;
import org.michaelbel.moviemade.ui.fragment.MovieFragment;
import org.michaelbel.moviemade.ui.fragment.ReviewsFragment;
import org.michaelbel.moviemade.ui.view.SettingsController;
import org.michaelbel.moviemade.ui.view.SettingsMenu;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

import java.util.Locale;

public class MovieActivity extends BaseActivity {

    public int movieId;
    public Movie movie;
    public MovieRealm movieRealm;

    private FragmentsPagerAdapter adapter;
    private SettingsMenu settingsMenu = new SettingsMenu();

    public ActivityMovieBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieRealm = getIntent().getParcelableExtra("movieRealm");

        /*if (savedInstanceState == null) {
            Uri data = getIntent().getData();
            String sMovieId;

            // Loading from deep link.
            if (data != null) {
                String[] parts = data.toString().split("/");
                sMovieId = parts[parts.length - 1];
                if (sMovieId == "movie") {
                    int dashPosition = sMovieId.indexOf("-");
                    if (dashPosition != -1) {
                        sMovieId = sMovieId.substring(0, dashPosition);
                    }
                    movieId = Integer.parseInt(sMovieId);
                }
            }
        }*/

        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        if (movie != null) {
            binding.toolbarTitle.setText(movie.title);

            adapter.addFragment(MovieFragment.newInstance(movie), R.string.Info);
            adapter.addFragment(MovieCastsFragment.newInstance(movie), R.string.Cast);
            adapter.addFragment(ReviewsFragment.newInstance(movie), R.string.Reviews);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movie), R.string.Similar);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movie), R.string.Related);
        } else if (movieRealm != null) {
            binding.toolbarTitle.setText(movieRealm.title);

            adapter.addFragment(MovieFragment.newInstance(movieRealm), R.string.Info);
            adapter.addFragment(MovieCastsFragment.newInstance(movieRealm), R.string.Cast);
            adapter.addFragment(ReviewsFragment.newInstance(movieRealm), R.string.Reviews);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movieRealm), R.string.Similar);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movieRealm), R.string.Related);
        } else {
            //binding.toolbarTitle.setText("Movie Info: " + sMovieId);

            //adapter.addFragment(MovieFragment.newInstance(movieId), R.string.Info);
            //adapter.addFragment(CastMovieFragment.newInstance(movieId), R.string.Cast);
            //adapter.addFragment(ReviewsMovieFragment.newInstance(movieId), R.string.Reviews);
            //adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movieId), R.string.Similar);
            //adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movieId), R.string.Related);
        }

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Share)
            .setIcon(R.drawable.ic_share)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, Url.TMDB_MOVIE, movie.id));
                startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (binding.viewPager.getCurrentItem() == 0) {
            MovieFragment fragment = (MovieFragment) adapter.getItem(0);
            if (fragment.isOpenImage()) {
                fragment.exitImage();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    public SettingsController getSettingsController() {
        return settingsMenu;
    }
}