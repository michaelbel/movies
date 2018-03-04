package org.michaelbel.moviemade.ui;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureImageView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
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

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public ViewPager viewPager;
    public TabLayout tabLayout;

    public Toolbar imageToolbar;
    public View fullBackground;
    public GestureImageView posterFull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        imageToolbar = findViewById(R.id.image_toolbar);
        fullBackground = findViewById(R.id.demo_full_background);
        posterFull = findViewById(R.id.poster_full);

        //MobileAds.initialize(this, AndroidUtils.loadProperty("appId", "admob.properties"));

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

        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        if (movie != null) {
            toolbarTitle.setText(movie.title);

            adapter.addFragment(MovieFragment.newInstance(movie), R.string.Info);
            adapter.addFragment(MovieCastsFragment.newInstance(movie), R.string.Cast);
            adapter.addFragment(ReviewsFragment.newInstance(movie), R.string.Reviews);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movie), R.string.Similar);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movie), R.string.Related);
        } else if (movieRealm != null) {
            toolbarTitle.setText(movieRealm.title);

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

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Share)
            .setIcon(R.drawable.avd_share)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                Drawable icon = menu.getItem(0).getIcon();
                if (icon instanceof Animatable) {
                    ((Animatable) icon).start();
                }

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
        if (viewPager.getCurrentItem() == 0) {
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