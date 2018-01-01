package org.michaelbel.application.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.PEOPLE;
import org.michaelbel.application.rest.model.People;
import org.michaelbel.application.rest.response.PeopleResponce;
import org.michaelbel.application.ui.PopularPeopleActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.CastView;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.AndroidUtilsDev;
import org.michaelbel.application.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class PopularPeopleFragment extends Fragment {

    private final int TOTAL_PAGES = 1000;

    private int page;
    private boolean isLoading;

    private PeopleAdapter adapter;
    private PopularPeopleActivity activity;
    private LinearLayoutManager layoutManager;
    private List<People> peopleList = new ArrayList<>();

    private TextView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    public static PopularPeopleFragment newInstance() {
        Bundle args = new Bundle();

        PopularPeopleFragment fragment = new PopularPeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (PopularPeopleActivity) getActivity();

        fragmentView = new SwipeRefreshLayout(getContext());
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(getContext(), Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.notConnected()) {
                onLoadError();
            } else {
                if (peopleList.isEmpty()) {
                    loadPopularPeople();
                } else {
                    fragmentView.setRefreshing(false);
                }
            }
        });

        FrameLayout fragmentContent = new FrameLayout(activity);
        fragmentContent.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentContent.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(fragmentContent);

        progressBar = new ProgressBar(activity);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentView.addView(progressBar);

        emptyView = new TextView(activity);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentContent.addView(emptyView);

        page = 1;
        adapter = new PeopleAdapter();
        layoutManager = new LinearLayoutManager(activity);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbarsEnabled());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            //Cast cast = peopleList.get(position);
            //activity.startPerson(cast);
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            return true;
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == peopleList.size() - 1 && !isLoading) {
                    if (page < TOTAL_PAGES) {
                        loadPopularPeople();
                    }
                }
            }
        });
        fragmentContent.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (NetworkUtils.notConnected()) {
            onLoadError();
        } else {
            loadPopularPeople();
        }
    }

    private void loadPopularPeople() {
        PEOPLE service = ApiFactory.getRetrofit().create(PEOPLE.class);
        Call<PeopleResponce> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<PeopleResponce>() {
            @Override
            public void onResponse(Call<PeopleResponce> call, Response<PeopleResponce> response) {
                if (response.isSuccessful()) {
                    if (AndroidUtils.includeAdult()) {
                        peopleList.addAll(response.body().peopleList);
                    } else {
                        for (People people : response.body().peopleList) {
                            if (!people.adult) {
                                peopleList.add(people);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (peopleList.isEmpty()) {
                        emptyView.setText(R.string.NoPeople);
                    } else {
                        page++;
                        isLoading = false;
                    }

                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<PeopleResponce> call, Throwable t) {
                onLoadError();
                isLoading = false;
            }
        });

        isLoading = true;
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
        emptyView.setText(R.string.NoConnection);
    }

    private class PeopleAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new CastView(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            People people = peopleList.get(position);

            CastView view = (CastView) holder.itemView;
            view.setName(people.name)
                .setCharacter(String.valueOf(people.popularity))
                .setProfileImage(people.profilePath)
                .setDivider(position != peopleList.size() - 1);
        }

        @Override
        public int getItemCount() {
            return peopleList != null ? peopleList.size() : 0;
        }
    }
}