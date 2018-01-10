package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.michaelbel.moviemade.app.ApiFactory;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.CreditResponse;
import org.michaelbel.moviemade.MovieActivity;
import org.michaelbel.moviemade.ui.adapter.Holder;
import org.michaelbel.moviemade.ui.view.CastView;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastMovieFragment extends Fragment {

    private Movie currentMovie;

    private CastAdapter adapter;
    private MovieActivity activity;
    private LinearLayoutManager linearLayoutManager;
    private List<Cast> persons = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    public static CastMovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);

        CastMovieFragment fragment = new CastMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MovieActivity) getActivity();
        activity.binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (AndroidUtils.scrollToTop()) {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.notConnected()) {
                onLoadError();
            } else {
                if (persons.isEmpty()) {
                    loadCredits();
                } else {
                    fragmentView.setRefreshing(false);
                }
            }
        });

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(activity);
        progressBar.setVisibility(persons.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        adapter = new CastAdapter();
        linearLayoutManager = new LinearLayoutManager(activity);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Cast cast = persons.get(position);
            //activity.startPerson(cast);
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            return true;
        });
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            currentMovie = (Movie) getArguments().getSerializable("movie");
        }

        if (NetworkUtils.notConnected()) {
            onLoadError();
        } else {
            loadCredits();
        }
    }

    private void loadCredits() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<CreditResponse> call = service.getCredits(currentMovie.id, Url.TMDB_API_KEY);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreditResponse> call, @NonNull Response<CreditResponse> response) {
                if (response.isSuccessful()) {
                    List<Cast> newPersons = new ArrayList<>();
                    newPersons.addAll(response.body().casts);

                    persons.clear();
                    persons.addAll(newPersons);
                    adapter.notifyItemRangeInserted(persons.size() + 1, newPersons.size());

                    if (!persons.isEmpty()) {
                        emptyView.setMode(EmptyView.MODE_NO_PEOPLE);
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable t) {
                onLoadError();
            }
        });
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
    }

    private class CastAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new CastView(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Cast cast = persons.get(position);

            CastView view = (CastView) holder.itemView;
            view.setName(cast.name)
                .setCharacter(cast.character)
                .setProfile(cast.profilePath)
                .setDivider(position != persons.size() - 1);
        }

        @Override
        public int getItemCount() {
            return persons != null ? persons.size() : 0;
        }
    }
}