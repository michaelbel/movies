package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.mvp.presenter.PopularPeoplePresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.ui.PopularPeopleActivity;
import org.michaelbel.moviemade.ui.adapter.pagination.PaginationPeopleAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.PersonView;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

import java.util.List;

public class PopularPeopleFragment extends MvpAppCompatFragment implements MvpResultsView {

    private PopularPeopleActivity activity;
    private PaginationPeopleAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    @InjectPresenter
    public PopularPeoplePresenter presenter;

    public static PopularPeopleFragment newInstance() {
        Bundle args = new Bundle();

        PopularPeopleFragment fragment = new PopularPeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (PopularPeopleActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.binding.toolbarTitle.setOnClickListener(v -> {
            if (AndroidUtils.scrollToTop()) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (adapter.getPeople().isEmpty()) {
                presenter.loadFirstPage();
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        FrameLayout fragmentContent = new FrameLayout(activity);
        fragmentContent.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentContent.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(fragmentContent);

        progressBar = new ProgressBar(activity);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentContent.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentContent.addView(emptyView);

        adapter = new PaginationPeopleAdapter();
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (view instanceof PersonView) {
                People person = (People) adapter.getPeople().get(position);
                activity.startPerson(person);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = linearLayoutManager.getItemCount();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!presenter.isLoading && !presenter.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0/* && totalItemCount >= presenter.totalPages*/) {
                        presenter.isLoading = true;
                        presenter.page++;
                        presenter.loadNextPage();
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

        if (savedInstanceState == null) {
            presenter.loadFirstPage();
        }
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        if (firstPage) {
            fragmentView.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

            adapter.addAll(results);

            if (presenter.page <= presenter.totalPages) {
                adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }
        } else {
            adapter.removeLoadingFooter();
            presenter.isLoading = false;
            adapter.addAll(results);

            if (presenter.page != presenter.totalPages) {
                adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }
        }
    }

    @Override
    public void showError(int mode) {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }

    /*private void updateList(List<People> newPeople) {
        PeopleDiffutilCallback callback = new PeopleDiffutilCallback(this.people, newPeople);
        DiffUtil.DiffResult peopleDiffResults = DiffUtil.calculateDiff(callback);
        adapter.notifyItemInserted(people.size());
        peopleDiffResults.dispatchUpdatesTo(adapter);
    }*/

    /*private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
    }*/

    /*private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentView.setRefreshing(false);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
    }*/

    /*private class PeopleDiffutilCallback extends DiffUtil.Callback {

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
    }*/
}