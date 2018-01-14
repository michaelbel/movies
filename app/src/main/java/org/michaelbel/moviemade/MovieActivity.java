package org.michaelbel.moviemade;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.databinding.ActivityMovieBinding;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.fragment.CastMovieFragment;
import org.michaelbel.moviemade.ui.fragment.ListMoviesFragment;
import org.michaelbel.moviemade.ui.fragment.MovieFragment;
import org.michaelbel.moviemade.ui.fragment.ReviewsMovieFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

import java.util.Locale;

public class MovieActivity extends BaseActivity {

    public Movie movie;
    public MovieRealm movieRealm;
    public ActivityMovieBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        if (savedInstanceState == null) {
            movie = (Movie) getIntent().getSerializableExtra("movie");
            movieRealm = (MovieRealm) getIntent().getParcelableExtra("movieRealm");
        }

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        //binding.toolbarTitle.setText(movie.title);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        if (movie != null) {
            binding.toolbarTitle.setText(movie.title);

            adapter.addFragment(MovieFragment.newInstance(movie), R.string.Info);
            adapter.addFragment(CastMovieFragment.newInstance(movie), R.string.Cast);
            adapter.addFragment(ReviewsMovieFragment.newInstance(movie), R.string.Reviews);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movie), R.string.Similar);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movie), R.string.Related);
        } else if (movieRealm != null) {
            binding.toolbarTitle.setText(movieRealm.title);

            adapter.addFragment(MovieFragment.newInstance(movieRealm), R.string.Info);
            adapter.addFragment(CastMovieFragment.newInstance(movieRealm), R.string.Cast);
            adapter.addFragment(ReviewsMovieFragment.newInstance(movieRealm), R.string.Reviews);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movieRealm), R.string.Similar);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movieRealm), R.string.Related);
        }
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        // todo Check, if true - implement everywhere
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
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
                    intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, Url.TMDB_MOVIE, movie.id));
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
}