package org.michaelbel.moviemade.modules_beta.person;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.tabs.TabLayout;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.eventbus.Events;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.ui.modules.main.ResultsMvp;
import org.michaelbel.moviemade.ui.modules.main.ListMoviesPresenter;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.ui.adapters.PaginationMoviesAdapter;
import org.michaelbel.moviemade.modules_beta.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.data.TmdbObject;
import org.michaelbel.moviemade.data.dao.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.functions.Consumer;

public class ListMoviesFragment extends MvpAppCompatFragment implements ResultsMvp {

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
        //args.putParcelable("movieRealm", movie);

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
            /*((MovieActivity) getActivity()).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {}

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if (AndroidUtils.scrollToTop()) {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }
            });*/
        } else if (getActivity() instanceof MainActivity) {
            /*((MainActivity) getActivity()).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {}

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if (AndroidUtils.scrollToTop()) {
                        recyclerView.smoothScrollToPosition(0);
                    }
                }
            });*/
        } else if (getActivity() instanceof PersonActivity) {
            ((PersonActivity) getActivity()).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {}

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

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
        fragmentView.setColorSchemeResources(R.color.accent);
        fragmentView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.background));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.primary));
        fragmentView.setOnRefreshListener(() -> {
            if (adapter.getList().isEmpty()) {
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
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.accent), PorterDuff.Mode.MULTIPLY);
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
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS ? 1 : 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).startMovie(movie);
            } else if (getActivity() instanceof MovieActivity) {
                ((MovieActivity) getActivity()).startMovie(movie);
            } else if (getActivity() instanceof PersonActivity) {
                ((PersonActivity) getActivity()).startMovie(movie);
            }
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getList().get(position);
            //boolean favorite = presenter.isMovieFavorite(movie.id);
            boolean watchlist = presenter.isMovieWatchlist(movie.getId());

            //int favoriteIcon = favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
            int watchlistIcon = watchlist ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_outline;
            //int favoriteText = favorite ? R.string.RemoveFromFavorites : R.string.AddToFavorites;
            int watchlistText = watchlist ? R.string.RemoveFromWatchList : R.string.AddToWatchlist;

            /*BottomSheet.Builder builder = new BottomSheet.Builder(getContext());
            builder.setCellHeight(ScreenUtils.dp(52));
            builder.setIconColorRes(Theme.iconActiveColor());
            builder.setItemTextColorRes(Theme.primaryTextColor());
            builder.setBackgroundColorRes(Theme.foregroundColor());
            builder.setItems(new int[] { favoriteText, watchlistText }, new int[] { favoriteIcon, watchlistIcon }, (dialog, i) -> {
                if (i == 0) {
                    presenter.movieFavoritesChange(movie);
                } else if (i == 1) {
                    presenter.movieWatchlistChange(movie);
                }
            });
            if (AndroidUtils.additionalOptions()) {
                builder.show();
            }*/
            return true;
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = gridLayoutManager.getItemCount();
                int visibleItemCount = gridLayoutManager.getChildCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                if (!presenter.isLoading && !presenter.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
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
            movieId = movie.getId();
        } else if (movieRealm != null) {
            movieId = movieRealm.id;
        } else if (person != null) {
            personId = person.id;
        }

        if (movieList == LIST_BY_PERSON) {
            presenter.loadPersonMovies(personId);
        } else {
            loadList();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(1);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();

        ((Moviemade) getActivity().getApplication()).eventBus().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                if (o instanceof Events.MovieListRefreshLayout) {
                    refreshLayout();
                } else if (o instanceof Events.ChangeTheme) {
                    changeTheme();
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
    public void showResults(List<Movie> results, boolean firstPage) {
        if (firstPage) {
            fragmentView.setRefreshing(false);
            progressBar.setVisibility(View.GONE);

            adapter.addAll(results);

            if (movieList != LIST_RELATED) {
                presenter.isLastPage = presenter.page < presenter.totalPages;
            }
        } else {
            adapter.removeLoadingFooter();
            presenter.isLoading = false;
            adapter.addAll(results);

            if (presenter.page == presenter.totalPages) {
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
        }
    }

    private void refreshLayout() {
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS ? 1 : 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
            recyclerView.addItemDecoration(itemDecoration);
        } else if (AndroidUtils.viewType() == 1) {
            itemDecoration.setOffset(ScreenUtils.dp(2));
            recyclerView.addItemDecoration(itemDecoration);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }

    private void changeTheme() {
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
    }
}