package org.michaelbel.application.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivityMovieBinding;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.model.Cast;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Review;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.base.BaseActivity;
import org.michaelbel.application.ui.base.BaseActivityModel;
import org.michaelbel.application.ui.base.BasePresenter;
import org.michaelbel.application.ui.fragment.CastMovieFragment;
import org.michaelbel.application.ui.fragment.ListMoviesFragment;
import org.michaelbel.application.ui.fragment.MovieFragment;
import org.michaelbel.application.ui.fragment.ReviewsMovieFragment;
import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.application.util.AndroidUtilsDev;

import java.util.ArrayList;

//@SuppressWarnings("all")
public class MovieActivity extends BaseActivity implements BaseActivityModel {

    public Movie movie;
    public ActivityMovieBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        if (savedInstanceState == null) {
            movie = (Movie) getIntent().getSerializableExtra("movie");
        }

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.toolbarTitle.setText(movie.title);

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(MovieFragment.newInstance(movie), R.string.Info);
        adapter.addFragment(CastMovieFragment.newInstance(movie), R.string.Cast);
        adapter.addFragment(ReviewsMovieFragment.newInstance(movie), R.string.Reviews);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_SIMILAR, movie), R.string.Similar);
        adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_RELATED, movie), R.string.Related);
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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

    public void startGenre(Genre genre) {
        Intent intent = new Intent(this, GenresActivity.class);
        intent.putExtra("genre", genre);
        startActivity(intent);
    }

    public void startGenres(ArrayList<Genre> list) {
        Intent intent = new Intent(this, GenresActivity.class);
        intent.putExtra("list", list);
        startActivity(intent);
    }
}