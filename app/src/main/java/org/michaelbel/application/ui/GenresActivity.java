package org.michaelbel.application.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivityGenresBinding;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.GENRES;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.MovieGenresResponse;
import org.michaelbel.application.ui.mvp.BaseActivity;
import org.michaelbel.application.ui.mvp.BaseActivityModel;
import org.michaelbel.application.ui.mvp.BasePresenter;
import org.michaelbel.application.ui.fragment.GenreMoviesFragment;
import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class GenresActivity extends BaseActivity implements BaseActivityModel {

    private ArrayList<Genre> genres;

    public FragmentsPagerAdapter adapter;

    public ActivityGenresBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_genres);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.emptyView.setText(R.string.NoConnection);
        binding.emptyView.setVisibility(View.INVISIBLE);
        binding.emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        binding.emptyView.setTextColor(ContextCompat.getColor(this, Theme.secondaryTextColor()));

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());

        binding.contentLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.backgroundColor()));

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        binding.tabLayout.setVisibility(View.INVISIBLE);

        Genre genre = (Genre) getIntent().getSerializableExtra("genre");

        if (genres != null) {
            if (!genres.isEmpty()) {
                genres.clear();
            }
        }
        genres = ((ArrayList<Genre>) getIntent().getSerializableExtra("list"));

        if (genre != null) {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.toolbarTitle.setText(genre.name);
            adapter.addFragment(GenreMoviesFragment.newInstance(genre.id));
            adapter.notifyDataSetChanged();
        } else if (genres != null) {
            binding.toolbarTitle.setText(R.string.LoadingGenres);
            loadExtraGenres();
        } else {
            binding.toolbarTitle.setText(R.string.LoadingGenres);
            loadGenres();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadGenres() {
        GENRES service = ApiFactory.getRetrofit().create(GENRES.class);
        Call<MovieGenresResponse> call = service.getMovieList(Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<MovieGenresResponse>() {
            @Override
            public void onResponse(Call<MovieGenresResponse> call, Response<MovieGenresResponse> response) {
                if (response.isSuccessful()) {
                    for (Genre genre : response.body().genres) {
                        adapter.addFragment(GenreMoviesFragment.newInstance(genre.id), genre.name);
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<MovieGenresResponse> call, Throwable t) {
                onLoadError();
            }
        });

        /*GENRES service = ApiFactory.getRetrofit().create(GENRES.class);
        Call<List<Genre>> call2 = service.getMovieList2(Url.TMDB_API_KEY, Url.en_US);
        call2.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                if (response.isSuccessful()) {
                    for (Genre genre : response.body()) {
                        adapter.addFragment(GenreMoviesFragment.newInstance(genre.id), genre.name);
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                onLoadError();
            }
        });*/
    }

    public void loadExtraGenres() {
        //adapter.getFragmentList().clear();

        for (Genre genre : genres) {
            adapter.addFragment(GenreMoviesFragment.newInstance(genre.id), genre.name);
            onLoadSuccessful();
        }
    }

    private void onLoadSuccessful() {
        adapter.notifyDataSetChanged();

        binding.toolbarLayout.removeView(binding.progressBar);
        binding.toolbarTitle.setVisibility(View.INVISIBLE);
        binding.tabLayout.setVisibility(View.VISIBLE);
    }

    private void onLoadError() {
        binding.emptyView.setVisibility(View.VISIBLE);
        binding.toolbarLayout.removeView(binding.progressBar);
        binding.toolbarTitle.setText(R.string.Genres);
    }

    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}