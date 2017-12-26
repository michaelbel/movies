package org.michaelbel.application.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.SEARCH;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.MovieResponse;
import org.michaelbel.application.ui.SearchActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.MovieViewList2;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.KeyboardUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchActivity activity;
    private SearchMovieAdapter adapter;
    private List<Movie> searchResults = new ArrayList<>();

    private TextView emptyView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (SearchActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        setHasOptionsMenu(true);

        progressBar = fragmentView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        emptyView = fragmentView.findViewById(R.id.empty_view);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        emptyView.setText(R.string.NoResults);

        adapter = new SearchMovieAdapter();

        RecyclerListView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = searchResults.get(position);
            activity.startMovie(movie.id, movie.title);
        });
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        //searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.Search));
        searchView.setMaxWidth(getResources().getDisplayMetrics().widthPixels);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                KeyboardUtils.hideKeyboard(searchView);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        AutoCompleteTextView searchTextView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        Theme.clearCursorDrawable(searchTextView);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, null);
        } catch (Exception e) {
        }
        searchTextView.setTextColor(ContextCompat.getColor(activity, Theme.foregroundColor()));


        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(String query) {
        searchResults.clear();
        emptyView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        performSearch(query);
    }

    public void performSearch(String query) {
        String finalQuery = query.trim().replace(" ", "-");

        SEARCH service = ApiFactory.getRetrofit().create(SEARCH.class);
        Call<MovieResponse> call = service.searchMovies(Url.SEARCH_FOR_MOVIES, Url.TMDB_API_KEY, Url.en_US, finalQuery);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    SharedPreferences prefs = activity.getSharedPreferences("main_config", Context.MODE_PRIVATE);
                    boolean adult = prefs.getBoolean("adult", true);

                    if (adult) {
                        searchResults.addAll(response.body().movieList);
                    } else {
                        for (Movie movie : response.body().movieList) {
                            if (!movie.adult) {
                                searchResults.add(movie);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (searchResults.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        emptyView.setText(R.string.NoResults);
                    }

                    onLoadSuccessful();
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //FirebaseCrash.report(t);
            }
        });
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public class SearchMovieAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            return new Holder(new MovieViewList2(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Movie movie = searchResults.get(position);

            MovieViewList2 view = (MovieViewList2) holder.itemView;
            view.setPoster(movie.posterPath);
            view.setTitle(movie.title);
            view.setYear(movie.releaseDate);
            view.setRating(movie.voteAverage);
            view.setDivider(position != searchResults.size() - 1);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public int getItemCount() {
            return searchResults != null ? searchResults.size() : 0;
        }
    }
}