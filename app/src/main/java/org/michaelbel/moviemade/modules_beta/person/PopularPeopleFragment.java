package org.michaelbel.moviemade.modules_beta.person;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.eventbus.Events;
import org.michaelbel.moviemade.modules_beta.view.PersonView;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.ui.modules.main.ResultsMvp;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.functions.Consumer;

public class PopularPeopleFragment extends MvpAppCompatFragment implements ResultsMvp {

    private PopularPeopleActivity activity;
    private PaginationPeopleAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    //private PeekAndPop peekAndPop;

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

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.toolbarTitle.setOnClickListener(v -> {
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
            if (adapter.getList().isEmpty()) {
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
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, Theme.accentColor()), PorterDuff.Mode.MULTIPLY);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentContent.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentContent.addView(emptyView);

        recyclerView = new RecyclerListView(activity);

        /*peekAndPop = new PeekAndPop.Builder(activity)
                .blurBackground(false)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(recyclerView)
                .build();*/

        adapter = new PaginationPeopleAdapter();
        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);

        //recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (view instanceof PersonView) {
                //People person = (People) adapter.getList().get(position);
                //activity.startPerson(person);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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

        /*peekAndPop = new PeekAndPop.Builder(activity)
                .blurBackground(false)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(recyclerView)
                //.parentViewGroupToDisallowTouchEvents(fragmentContent)
                .build();*/
        /*peekAndPop.setOnLongHoldListener(new PeekAndPop.OnLongHoldListener() {
            @Override
            public void onEnter(View view, int position) {

            }

            @Override
            public void onLongHold(View view, int position) {
                if (view.getId() == R.id.arrow_down) {
                    ((Vibrator) activity.getSystemService(Activity.VIBRATOR_SERVICE)).vibrate(0);
                    Log.e("2580", "WE DID IT!");
                }
            }
        });*/

        //recyclerView.setAdapter(new PeekAdapter(buildDemoObjects(), peekAndPop));

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //if (savedInstanceState == null) {
            presenter.loadFirstPage();
        //}
    }

    /*@Override
    public void onDestroy(){
        super.onDestroy();
        peekAndPop.destroy();
    }*/

    @Override
    public void showResults(List<Movie> results, boolean firstPage) {
        if (firstPage) {
            fragmentView.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

          //  adapter.addAll(results);

            if (presenter.page <= presenter.totalPages) {
                adapter.addLoadingFooter();
            } else {
                presenter.isLastPage = true;
            }
        } else {
            adapter.removeLoadingFooter();
            presenter.isLoading = false;
           // adapter.addAll(results);

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

    @Override
    public void onResume() {
        super.onResume();

        ((Moviemade) activity.getApplication()).eventBus().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof Events.ChangeTheme) {
                    changeTheme();
                }
            }
        });
    }

    private void changeTheme() {
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
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