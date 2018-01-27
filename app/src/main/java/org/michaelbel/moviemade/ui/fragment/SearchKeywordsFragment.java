package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.presenter.SearchKeywordsPresenter;
import org.michaelbel.moviemade.mvp.view.MvpSearchView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.ui.SearchActivity;
import org.michaelbel.moviemade.ui.adapter.pagination.PaginationKeywordsAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.cell.TextCell;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

import java.util.List;

public class SearchKeywordsFragment extends MvpAppCompatFragment implements MvpSearchView {

    private String readyQuery;

    private SearchActivity activity;
    private PaginationKeywordsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;

    @InjectPresenter
    public SearchKeywordsPresenter presenter;

    public static SearchKeywordsFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);

        SearchKeywordsFragment fragment = new SearchKeywordsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SearchActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        emptyView = new EmptyView(activity);
        emptyView.setMode(EmptyViewMode.MODE_NO_RESULTS);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentView.addView(emptyView);

        progressBar = new ProgressBar(activity);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentView.addView(progressBar);

        adapter = new PaginationKeywordsAdapter();
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (view instanceof TextCell) {
                Keyword keyword = (Keyword) adapter.getKeywords().get(position);
                activity.startKeyword(keyword);
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
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        readyQuery = getArguments().getString("query");

        if (readyQuery == null) {
            activity.searchEditText.setSelection(activity.searchEditText.getText().length());
        } else {
            activity.searchEditText.setText(readyQuery);
            activity.searchEditText.setSelection(activity.searchEditText.getText().length());

            AndroidUtils.hideKeyboard(activity.searchEditText);
            presenter.search(readyQuery);
        }
    }

    @Override
    public void searchStart() {
        adapter.getKeywords().clear();
        adapter.notifyDataSetChanged();

        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        if (firstPage) {
            progressBar.setVisibility(View.GONE);

            adapter.addAll(results);

            if (presenter.page < presenter.totalPages) {
                adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }

            if (AndroidUtilsDev.searchResultsCount()) {
                TabLayout.Tab tab = activity.binding.tabLayout.getTabAt(SearchActivity.TAB_KEYWORDS);
                if (tab != null) {
                    tab.setText(getResources().getQuantityString(R.plurals.KeywordsTotalResults, presenter.totalResults, presenter.totalResults));
                }
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
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);

        TabLayout.Tab tab = activity.binding.tabLayout.getTabAt(SearchActivity.TAB_PEOPLE);
        if (tab != null) {
            tab.setText(R.string.People);
        }
    }

    public boolean empty() {
        return adapter.getKeywords().isEmpty();
    }
}