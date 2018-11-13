package org.michaelbel.moviemade.modules_beta.collection;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.ui.modules.main.ResultsMvp;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewPoster;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CollectionFragment extends MvpAppCompatFragment implements ResultsMvp {

    private Collection collection;

    private MoviesAdapter adapter;
    private CollectionActivity activity;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;
    private SwipeRefreshLayout fragmentView;
    private TextView overviewText;

    @InjectPresenter
    public CollectionPresenter presenter;

    public static CollectionFragment newInstance(Collection collection) {
        Bundle args = new Bundle();
        //args.putParcelable("collection", collection);

        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CollectionActivity) getActivity();
    }

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
            if (adapter.getMovies().isEmpty()) {
                presenter.loadMovies(collection.id);
            } else {
                fragmentView.setRefreshing(false);
            }
        });

        //ScrollView scrollView = new ScrollView(activity);
        //scrollView.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        //fragmentView.addView(scrollView);

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(contentLayout);

        //LinearLayout collectionViewLayout = new LinearLayout(activity);
        //collectionViewLayout.setOrientation(LinearLayout.VERTICAL);
        //collectionViewLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        //contentLayout.addView(collectionViewLayout);

        progressBar = new ProgressBar(activity);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, Theme.accentColor()), PorterDuff.Mode.MULTIPLY);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(getContext());
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        // Content

        //overviewText = new TextView(activity);
        //overviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        //collectionViewLayout.addView(overviewText);

        itemDecoration = new PaddingItemDecoration();
        itemDecoration.setOffset(AndroidUtils.viewType() == 0 ? 0 : ScreenUtils.dp(1));

        adapter = new MoviesAdapter();
        gridLayoutManager = new GridLayoutManager(getContext(), AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS ? 1 : 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView = new RecyclerListView(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (view instanceof MovieViewListBig || view instanceof MovieViewPoster) {
                Movie movie = (Movie) adapter.getMovies().get(position);
                activity.startMovie(movie);
            }
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            Movie movie = (Movie) adapter.getMovies().get(position);
            //boolean favorite = presenter.isMovieFavorite(movie.id);
            boolean watchlist = presenter.isMovieWatchlist(movie.getId());

            //int favoriteIcon = favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
            int watchlistIcon = watchlist ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_outline;
            //int favoriteText = favorite ? R.string.RemoveFromFavorites : R.string.AddToFavorites;
            int watchlistText = watchlist ? R.string.RemoveFromWatchList : R.string.AddToWatchlist;

            /*BottomSheet.Builder builder = new BottomSheet.Builder(activity);
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
        contentLayout.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        collection = getArguments().getParcelable("collection");

        //overviewText.setText(collection.overview);
        /*Picasso.with(getContext())
                .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.backdropSize(), collection.backdropPath))
                .placeholder(R.drawable.movie_placeholder_old)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(activity.binding.backdropView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //progressBar.setVisibility(GONE);
                    }

                    @Override
                    public void onError() {
                        //progressBar.setVisibility(GONE);
                    }
                });*/

        //activity.binding.collapsingLayout.setTitle(collection.name);

        if (savedInstanceState == null) {
            presenter.loadMovies(collection.id);
        }
    }

    @Override
    public void showResults(List<Movie> results, boolean firstPage) {
        adapter.addMovies(results);
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int mode) {
        fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(mode);
    }
}