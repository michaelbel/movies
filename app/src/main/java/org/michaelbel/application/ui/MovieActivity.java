package org.michaelbel.application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.ui.fragment.CastMovieFragment;
import org.michaelbel.application.ui.fragment.MovieFragment;
import org.michaelbel.application.ui.fragment.RelatedMovieFragment;
import org.michaelbel.application.ui.fragment.ReviewsMovieFragment;
import org.michaelbel.application.ui.fragment.SimilarMoviesFragment;

public class MovieActivity extends AppCompatActivity {

    private int movieId;
    private String movieTitle;

    public Toolbar toolbar;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    public TextView toolbarTextView;
    public AppBarLayout appBarLayout;
    public FloatingActionButton fabButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0x33000000);

        if (savedInstanceState == null) {
            movieId = getIntent().getIntExtra("movieId", 0);
            movieTitle = getIntent().getStringExtra("movieTitle");
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbarTextView = findViewById(R.id.toolbar_title);
        toolbarTextView.setText(movieTitle);

        appBarLayout = findViewById(R.id.app_bar);
        fabButton = findViewById(R.id.fab_button);
        viewPager = findViewById(R.id.view_pager);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(MovieFragment.newInstance(movieId), "Info");
        adapter.addFragment(CastMovieFragment.newInstance(movieId), "Cast");
        adapter.addFragment(ReviewsMovieFragment.newInstance(movieId), "Reviews");
        adapter.addFragment(SimilarMoviesFragment.newInstance(movieId), "Similar");
        adapter.addFragment(RelatedMovieFragment.newInstance(movieId), "Related");
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
                        intent.putExtra(Intent.EXTRA_TEXT, Url.TMDB_MOVIE + movieId);
                        startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
                        return true;
                    } catch (Exception e) {
                        //FirebaseCrash.report(e);
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

    public void startMovie(int movieId, String movieTitle) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("movieTitle", movieTitle);
        startActivity(intent);
    }

    public void startPerson(int personId, String personName) {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("personId", personId);
        intent.putExtra("personName", personName);
        startActivity(intent);
    }

    public void startReview(String reviewId) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("reviewId", reviewId);
        startActivity(intent);
    }
}