package org.michaelbel.application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.GENRES;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.MovieGenresResponse;
import org.michaelbel.application.ui.fragment.GenreMoviesFragment;
import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class GenresActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    public TextView toolbarTextView;

    public FragmentsPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbarTextView = findViewById(R.id.toolbar_title);
        toolbarTextView.setText(R.string.LoadingGenres);

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        tabLayout.setVisibility(View.INVISIBLE);

        fillAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillAdapter() {
        GENRES service = ApiFactory.getRetrofit().create(GENRES.class);
        Call<MovieGenresResponse> call = service.getMovieList(Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<MovieGenresResponse>() {
            @Override
            public void onResponse(Call<MovieGenresResponse> call, Response<MovieGenresResponse> response) {
                if (response.isSuccessful()) {
                    for (Genre genre : response.body().genresList) {
                        adapter.addFragment(GenreMoviesFragment.newInstance(genre.id), genre.name);
                    }

                    onLoadSuccessful();
                } else {
                    // todo Error
                }
            }

            @Override
            public void onFailure(Call<MovieGenresResponse> call, Throwable t) {
                // todo Error.
            }
        });
    }

    private void onLoadSuccessful() {
        // todo it is necessary?
        adapter.notifyDataSetChanged();

        toolbarTextView.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
    }

    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}