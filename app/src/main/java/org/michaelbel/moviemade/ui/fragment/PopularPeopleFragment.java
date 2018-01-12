package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
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
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.People;
import org.michaelbel.moviemade.rest.response.PeopleResponse;
import org.michaelbel.moviemade.PopularPeopleActivity;
import org.michaelbel.moviemade.ui.adapter.PeopleAdapter;
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

public class PopularPeopleFragment extends Fragment {

    private int page;
    private int totalPages;
    private boolean isLoading;

    private PeopleAdapter adapter;
    private PopularPeopleActivity activity;
    private LinearLayoutManager linearLayoutManager;
    private List<People> people = new ArrayList<>();

    private EmptyView emptyView;
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

        activity.binding.toolbarTitle.setOnClickListener(v -> {
            if (AndroidUtils.scrollToTop()) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (NetworkUtils.notConnected()) {
                onLoadError();
            } else {
                if (people.isEmpty()) {
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
        progressBar.setVisibility(people.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentContent.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentContent.addView(emptyView);

        page = 1;
        adapter = new PeopleAdapter(people);
        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            People p = (People) people.get(position);
            //activity.startPerson(cast);
        });
        //recyclerView.setOnItemLongClickListener((view, position) -> {
        //    return true;
        //});
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastVisibleItemPosition() == people.size() - 1 && !isLoading) {
                    if (page < totalPages) {
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
        Call<PeopleResponse> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(@NonNull Call<PeopleResponse> call, @NonNull Response<PeopleResponse> response) {
                if (!response.isSuccessful()) {
                    onLoadError();
                    return;
                }

                if (totalPages == 0) {
                    totalPages = response.body().totalPages;
                }

                List<People> newPeople = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    newPeople.addAll(response.body().people);
                } else {
                    for (People people : response.body().people) {
                        if (!people.adult) {
                            newPeople.add(people);
                        }
                    }
                }

                people.addAll(newPeople);
                adapter.notifyItemRangeInserted(people.size() + 1, newPeople.size());

                if (people.isEmpty()) {
                    emptyView.setMode(EmptyView.MODE_NO_PEOPLE);
                } else {
                    page++;
                    isLoading = false;
                }

                onLoadSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<PeopleResponse> call, @NonNull Throwable t) {
                isLoading = false;
                onLoadError();
            }
        });

        isLoading = true;
    }

    /*private void updateList(List<People> newPeople) {
        PeopleDiffutilCallback callback = new PeopleDiffutilCallback(this.people, newPeople);
        DiffUtil.DiffResult peopleDiffResults = DiffUtil.calculateDiff(callback);
        adapter.notifyItemInserted(people.size());
        peopleDiffResults.dispatchUpdatesTo(adapter);
    }*/

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
    }

    private class PeopleDiffutilCallback extends DiffUtil.Callback {

        private final List<People> oldPeople;
        private final List<People> newPeople;

        public PeopleDiffutilCallback(List<People> oldPeople, List<People> newPeople) {
            this.oldPeople = oldPeople;
            this.newPeople = newPeople;
        }

        @Override
        public int getOldListSize() {
            return oldPeople.size();
        }

        @Override
        public int getNewListSize() {
            return newPeople.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            People oldPeopleModel = oldPeople.get(oldItemPosition);
            People newPeopleModel = newPeople.get(newItemPosition);
            return oldPeopleModel.id == newPeopleModel.id;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            People oldPeopleModel = oldPeople.get(oldItemPosition);
            People newPeopleModel = newPeople.get(newItemPosition);
            return oldPeopleModel.name.equals(newPeopleModel.name);
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}