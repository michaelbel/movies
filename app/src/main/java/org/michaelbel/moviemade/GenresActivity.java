package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;

import org.michaelbel.moviemade.app.annotation.Beta;
import org.michaelbel.moviemade.databinding.ActivityGenresBinding;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.api.GENRES;
import org.michaelbel.moviemade.rest.model.Genre;
import org.michaelbel.moviemade.rest.response.MovieGenresResponse;
import org.michaelbel.moviemade.ui.fragment.GenreMoviesFragment;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenresActivity extends BaseActivity {

    private ArrayList<Genre> genres;
    public FragmentsPagerAdapter adapter;
    public ActivityGenresBinding binding;

    private EmptyView emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_genres);

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.swipeRefreshLayout.setRefreshing(false);
        binding.swipeRefreshLayout.setColorSchemeResources(Theme.accentColor());
        binding.swipeRefreshLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.backgroundColor()));
        binding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.toolbarTitle.setText(R.string.LoadingGenres);
                loadGenres();
            }
        });

        emptyView = new EmptyView(this);
        emptyView.setVisibility(View.GONE);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Beta
    public void loadGenres() {
        emptyView.setVisibility(View.INVISIBLE);

        GENRES service = ApiFactory.createService(GENRES.class);
        Call<MovieGenresResponse> call = service.getMovieList(Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<MovieGenresResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieGenresResponse> call, @NonNull Response<MovieGenresResponse> response) {
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
            public void onFailure(@NonNull Call<MovieGenresResponse> call, @NonNull Throwable t) {
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

    @Beta
    public void loadExtraGenres() {
        //adapter.getFragmentList().clear();

        for (Genre genre : genres) {
            adapter.addFragment(GenreMoviesFragment.newInstance(genre.id), genre.name);
            onLoadSuccessful();
        }
    }

    @Beta
    private void onLoadSuccessful() {
        adapter.notifyDataSetChanged();

        binding.swipeRefreshLayout.setRefreshing(false);
        binding.toolbarLayout.removeView(binding.progressBar);
        binding.toolbarTitle.setVisibility(View.INVISIBLE);
        binding.tabLayout.setVisibility(View.VISIBLE);
    }

    @Beta
    private void onLoadError() {
        binding.swipeRefreshLayout.setRefreshing(false);
        emptyView.setVisibility(View.VISIBLE);
        binding.toolbarLayout.removeView(binding.progressBar);
        binding.toolbarTitle.setText(R.string.Genres);
    }
}