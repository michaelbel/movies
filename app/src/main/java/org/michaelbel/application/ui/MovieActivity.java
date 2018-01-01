package org.michaelbel.application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.model.Cast;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Review;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.fragment.CastMovieFragment;
import org.michaelbel.application.ui.fragment.ListMoviesFragment;
import org.michaelbel.application.ui.fragment.MovieFragment;
import org.michaelbel.application.ui.fragment.ReviewsMovieFragment;
import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.application.util.AndroidUtilsDev;

import java.util.ArrayList;

@SuppressWarnings("all")
public class MovieActivity extends AppCompatActivity {

    public Movie movie;

    public Toolbar toolbar;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    public TextView toolbarTextView;
    public AppBarLayout appBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        if (savedInstanceState == null) {
            movie = (Movie) getIntent().getSerializableExtra("movie");
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);
        toolbar.setLayoutParams(params);

        toolbarTextView = findViewById(R.id.toolbar_title);
        toolbarTextView.setText(movie.title);

        appBarLayout = findViewById(R.id.app_bar);
        viewPager = findViewById(R.id.view_pager);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(MovieFragment.newInstance(movie), R.string.Info);
        adapter.addFragment(CastMovieFragment.newInstance(movie), R.string.Cast);
        adapter.addFragment(ReviewsMovieFragment.newInstance(movie), R.string.Reviews);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movie), R.string.Similar);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movie), R.string.Related);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Share)
                .setIcon(R.drawable.ic_share)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                .setOnMenuItemClickListener(menuItem -> {
                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, Url.TMDB_MOVIE + movie.id);
                        startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    public void startPerson(Cast person) {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    public void startReview(Review review, Movie movie) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("review", review);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    public void startTrailers(Movie movie, ArrayList<Trailer> list) {
        Intent intent = new Intent(this, TrailersActivity.class);
        intent.putExtra("movie", movie);
        intent.putExtra("list", list);
        startActivity(intent);
    }
}