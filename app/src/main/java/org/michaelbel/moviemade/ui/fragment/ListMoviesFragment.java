package org.michaelbel.moviemade.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.eventbus.Events;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.presenter.ListMoviesPresenter;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.MainActivity;
import org.michaelbel.moviemade.ui.MovieActivity;
import org.michaelbel.moviemade.ui.PersonActivity;
import org.michaelbel.moviemade.ui.adapter.pagination.PaginationMoviesAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.ui.view.movie.MovieViewPoster;
import org.michaelbel.moviemade.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class ListMoviesFragment extends MvpAppCompatFragment implements MvpResultsView {

    public static final int LIST_NOW_PLAYING = 1;
    public static final int LIST_POPULAR = 2;
    public static final int LIST_TOP_RATED = 3;
    public static final int LIST_UPCOMING = 4;
    public static final int LIST_SIMILAR = 5;
    public static final int LIST_RELATED = 6;
    public static final int LIST_BY_PERSON = 7;

    private int movieId;
    private int personId;

    private int movieList;
    private Movie movie;
    private MovieRealm movieRealm;
    private Cast person;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    private PaginationMoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

    @InjectPresenter
    public ListMoviesPresenter presenter;

    public static ListMoviesFragment newInstance(int list) {
        Bundle args = new Bundle();
        args.putInt("list", list);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListMoviesFragment newInstance(int list, Movie movie) {
        Bundle args = new Bundle();
        args.putInt("list", list);
        args.putSerializable("movie", movie);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListMoviesFragment newInstance(int list, MovieRealm movie) {
        Bundle args = new Bundle();
        args.putInt("list", list);
        args.putParcelable("movieRealm", movie);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListMoviesFragment newInstance(int list, Cast person) {
        Bundle args = new Bundle();
        args.putInt("list", list);
        args.putSerializable("person", person);

        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof MovieActivity) {
            ((MovieActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        } else if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        } else if (getActivity() instanceof PersonActivity) {
            ((PersonActivity) getActivity()).binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        }

        fragmentView = new SwipeRefreshLayout(getContext());
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(getContext(), Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), Theme.primaryColor()));
        fragmentView.setOnRefreshListener(() -> {
            if (adapter.getMovies().isEmpty()) {
                if (movieList == LIST_BY_PERSON) {
                    presenter.loadPersonMovies(personId);
                } else {
                    loadList();
                }
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        FrameLayout contentLayout = new FrameLayout(getContext());
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        adapter = new PaginationMoviesAdapter();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.getSpanForMovies());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (view instanceof MovieViewListBig || view instanceof MovieViewPoster) {
                Movie movie = (Movie) adapter.getMovies().get(position);

                if (getArguments().getSerializable("movie") != null) {
                    ((MovieActivity) getActivity()).startMovie(movie);
                } else if (getArguments().getSerializable("person") != null) {
                    ((PersonActivity) getActivity()).startMovie(movie);
                } else {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).startMovie(movie);
                    } else if (getActivity() instanceof MovieActivity) {
                        ((MovieActivity) getActivity()).startMovie(movie);
                    }
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = gridLayoutManager.getItemCount();
                int visibleItemCount = gridLayoutManager.getChildCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                if (!presenter.isLoading && !presenter.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0/* && totalItemCount >= presenter.totalPages*/) {
                        presenter.isLoading = true;
                        presenter.page++;
                        loadNextPage();
                    }
                }
            }
        });
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        movieList = getArguments().getInt("list");
        movie = (Movie) getArguments().getSerializable("movie");
        movieRealm = getArguments().getParcelable("movieRealm");
        person = (Cast) getArguments().getSerializable("person");

        if (movie != null) {
            movieId = movie.id;
        } else if (movieRealm != null) {
            movieId = movieRealm.id;
        } else if (person != null) {
            personId = person.id;
        }

        if (savedInstanceState == null) {
            if (movieList == LIST_BY_PERSON) {
                presenter.loadPersonMovies(personId);
            } else {
                loadList();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(1);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((Moviemade) getActivity().getApplication()).eventBus().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof Events.MovieListRefreshLayout) {
                    refreshLayout();
                }
            }
        });

            /*@Override
            public void call(Object object) {
                if (object instanceof Events.MovieListUpdateAdult) {
                    if (!NetworkUtils.notConnected()) {
                        if (!movies.isEmpty()) {
                            movies.clear();
                        }

                        if (movieList == LIST_BY_PERSON) {
                            loadPersonMovies();
                        } else {
                            loadList();
                        }
                    }
                } else if (object instanceof Events.MovieListUpdateImageQuality) {
                    if (!NetworkUtils.notConnected()) {
                        if (!movies.isEmpty()) {
                            movies.clear();
                        }

                        if (movieList == LIST_BY_PERSON) {
                            loadPersonMovies();
                        } else {
                            loadList();
                        }
                    }
                } else if (object instanceof Events.MovieListRefreshLayout) {
                    refreshLayout();
                }
            }*/
    }

    @Override
    public void showResults(List<TmdbObject> results, boolean firstPage) {
        if (firstPage) {
            fragmentView.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

            adapter.addAll(results);

            if (movieList != LIST_RELATED) { // todo eto pizdez
                if (presenter.page < presenter.totalPages) {
                    adapter.addLoadingFooter();
                } else {
                    presenter.isLastPage = true;
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
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }

    private void loadList() {
        if (movieList == LIST_NOW_PLAYING) {
            presenter.loadNowPlayingMovies();
        } else if (movieList == LIST_POPULAR) {
            presenter.loadPopularMovies();
        } else if (movieList == LIST_TOP_RATED) {
            presenter.loadTopRatedMovies();
        } else if (movieList == LIST_UPCOMING) {
            presenter.loadUpcomingMovies();
        } else if (movieList == LIST_SIMILAR) {
            presenter.loadSimilarMovies(movieId);
        } else if (movieList == LIST_RELATED) {
            presenter.loadRelatedMovies(movieId);
        }
    }

    private void loadNextPage() {
        if (movieList == LIST_NOW_PLAYING) {
            presenter.loadNowPlayingNextMovies();
        } else if (movieList == LIST_POPULAR) {
            presenter.loadPopularNextMovies();
        } else if (movieList == LIST_TOP_RATED) {
            presenter.loadTopRatedNextMovies();
        } else if (movieList == LIST_UPCOMING) {
            presenter.loadUpcomingNextMovies();
        } else if (movieList == LIST_SIMILAR) {
            presenter.loadSimilarNextMovies(movieId);
        } else if (movieList == LIST_RELATED) {
            //presenter.loadRelatedNextMovies(movieId);
        }
    }

    private void refreshLayout() {
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.getSpanForMovies());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
            recyclerView.addItemDecoration(itemDecoration);
        } else if (AndroidUtils.viewType() == 1) {
            itemDecoration.setOffset(ScreenUtils.dp(1));
            recyclerView.addItemDecoration(itemDecoration);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }
}