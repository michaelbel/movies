package org.michaelbel.moviemade.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import org.michaelbel.moviemade.MainActivity;
import org.michaelbel.moviemade.MovieActivity;
import org.michaelbel.moviemade.PersonActivity;
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
import org.michaelbel.moviemade.ui.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;
import org.michaelbel.moviemade.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class ListMoviesFragment extends MvpAppCompatFragment implements MvpResultsView {

    public static final int LIST_NOW_PLAYING = 1;
    public static final int LIST_POPULAR = 2;
    public static final int LIST_TOP_RATED = 3;
    public static final int LIST_UPCOMING = 4;
    public static final int LIST_SIMILAR = 5;
    public static final int LIST_RELATED = 6;
    public static final int LIST_BY_PERSON = 7;

    private int currentMovieList;
    private Movie currentMovie;
    private Cast currentPerson;

    private int movieId;
    private MovieRealm currentMovieRealm;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;

    private MoviesAdapter adapter;
    private PaddingItemDecoration itemDecoration;
    private GridLayoutManager gridLayoutManager;
    private List<TmdbObject> movies = new ArrayList<>();

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
            /*if (NetworkUtils.notConnected()) {
                onLoadError();
            } else {
                if (movies.isEmpty()) {
                    if (currentMovieList == LIST_BY_PERSON) {
                        loadPersonMovies();
                    } else {
                        loadList();
                    }
                } else {
                    fragmentView.setRefreshing(false);
                }
            }*/
            fragmentView.setRefreshing(false);
        });

        FrameLayout contentLayout = new FrameLayout(getContext());
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(getContext());
        progressBar.setVisibility(movies.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        adapter = new MoviesAdapter(movies);

        itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.getSpanForMovies());
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = (Movie) movies.get(position);

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
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (currentMovieList != LIST_BY_PERSON) {
                    if (gridLayoutManager.findLastVisibleItemPosition() == movies.size() - 1 && !presenter.loading && !presenter.loadingLocked) {
                        if (presenter.page < presenter.totalPages) {
                            loadNextPage();
                        }
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

        currentMovieList = getArguments().getInt("list");
        currentMovie = (Movie) getArguments().getSerializable("movie");
        currentMovieRealm = getArguments().getParcelable("movieRealm");

        if (currentMovie != null) {
            movieId = currentMovie.id;
        } else if (currentMovieRealm != null) {
            movieId = currentMovieRealm.id;
        }

        if (getArguments().getSerializable("person") != null) {
            currentPerson = (Cast) getArguments().getSerializable("person");
        }

        if (currentMovieList == LIST_BY_PERSON) {
            presenter.loadPersonMovies(currentPerson.id);
        } else {
            loadList();
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

        ((Moviemade) getActivity().getApplication()).eventBus().toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object object) {
                /*if (object instanceof Events.MovieListUpdateAdult) {
                    if (!NetworkUtils.notConnected()) {
                        if (!movies.isEmpty()) {
                            movies.clear();
                        }

                        if (currentMovieList == LIST_BY_PERSON) {
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

                        if (currentMovieList == LIST_BY_PERSON) {
                            loadPersonMovies();
                        } else {
                            loadList();
                        }
                    }
                } else*/ if (object instanceof Events.MovieListRefreshLayout) {
                    refreshLayout();
                }
            }
        });
    }

    @Override
    public void showResults(List<TmdbObject> results) {
        movies.addAll(results);
        adapter.notifyItemRangeInserted(movies.size() + 1, results.size());

        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int mode) {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }

    private void loadList() {
        if (currentMovieList == LIST_NOW_PLAYING) {
            //loadNowPlayingMovies();
            presenter.loadNowPlayingMovies();
        } else if (currentMovieList == LIST_POPULAR) {
            //loadPopularMovies();
            presenter.loadPopularMovies();
        } else if (currentMovieList == LIST_TOP_RATED) {
            //loadTopRatedMovies();
            presenter.loadTopRatedMovies();
        } else if (currentMovieList == LIST_UPCOMING) {
            //loadUpcomingMovies();
            presenter.loadUpcomingMovies();
        } else if (currentMovieList == LIST_SIMILAR) {
            //loadSimilarMovies();
            presenter.loadSimilarMovies(movieId);
        } else if (currentMovieList == LIST_RELATED) {
            //loadRelatedMovies();
            presenter.loadRelatedMovies(movieId);
        }
    }

    private void loadNextPage() {
        if (currentMovieList == LIST_NOW_PLAYING) {
            presenter.loadNowPlayingNextMovies();
        } else if (currentMovieList == LIST_POPULAR) {
            presenter.loadPopularNextMovies();
        } else if (currentMovieList == LIST_TOP_RATED) {
            presenter.loadTopRatedNextMovies();
        } else if (currentMovieList == LIST_UPCOMING) {
            presenter.loadUpcomingNextMovies();
        } else if (currentMovieList == LIST_SIMILAR) {
            presenter.loadSimilarNextMovies(movieId);
        } else if (currentMovieList == LIST_RELATED) {
            presenter.loadRelatedNextMovies(movieId);
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
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
            recyclerView.addItemDecoration(itemDecoration);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }
}