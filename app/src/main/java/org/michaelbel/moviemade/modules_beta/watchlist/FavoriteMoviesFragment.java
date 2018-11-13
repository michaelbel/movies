package org.michaelbel.moviemade.modules_beta.watchlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Url;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.modules_beta.view.movie.MovieViewListBig;
import org.michaelbel.moviemade.modules_beta.view.widget.PaddingItemDecoration;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteMoviesFragment extends Fragment {

    private MovieAdapter adapter;
    private MainActivity activity;
    private GridLayoutManager gridLayoutManager;
    private PaddingItemDecoration itemDecoration;
    private List<MovieRealm> movies = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem viewItem = menu.add(R.string.ChangeViewType);
        viewItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        viewItem.setIcon(R.drawable.ic_view_default);

        viewItem.setOnMenuItemClickListener(item -> {
            int newType = AndroidUtils.viewType();

            if (newType == 0) {
                newType = 1;
            } else if (newType == 1) {
                newType = 0;
            }

            SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
            prefs.edit().putInt("view_type", newType).apply();

            refreshLayout();
            return true;
        });
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        emptyView = view.findViewById(R.id.empty_view);

        adapter = new MovieAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, AndroidUtils.viewType() == AndroidUtils.VIEW_POSTERS ? 1 : 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        PaddingItemDecoration itemDecoration = new PaddingItemDecoration();
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(0);
        } else if (AndroidUtils.viewType() == 1) {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        } else if (AndroidUtils.viewType() == 2) {
            itemDecoration.setOffset(ScreenUtils.dp(1));
        }

        RecyclerListView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOnItemClickListener((v, position) -> {
            MovieRealm movie = movies.get(position);
            activity.startMovie(movie);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(getLayoutColumns());
    }*/

    private void load() {
        if (!movies.isEmpty()) {
            movies.clear();
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<MovieRealm> results = realm.where(MovieRealm.class).equalTo("favorite", true).findAll();
        if (results.isEmpty()) {
            onLoadError();
        } else {
            movies.addAll(results);
            Collections.reverse(movies);
            onLoadSuccessful();
        }

        adapter.notifyDataSetChanged();
    }

    private void onLoadError() {
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(EmptyViewMode.MODE_NO_MOVIES);
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.GONE);
    }

    /*private void refreshLayout() {
        Parcelable state = gridLayoutManager.onSaveInstanceState();
        gridLayoutManager = new GridLayoutManager(activity, getLayoutColumns());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.removeItemDecoration(itemDecoration);
        if (AndroidUtils.viewType() == 0) {
            itemDecoration.setOffset(ScreenUtils.dp(2));
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setPadding(ScreenUtils.dp(4), 0, ScreenUtils.dp(4), 0);
        } else {
            itemDecoration.setOffset(ScreenUtils.dp(1));
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setPadding(0, 0, 0, 0);
        }
        gridLayoutManager.onRestoreInstanceState(state);
    }*/

    /*public int getLayoutColumns() {
        if (AndroidUtils.viewType() == 0) {
            return ScreenUtils.isPortrait() ? 3 : 5;
        } else {
            return ScreenUtils.isPortrait() ? 2 : 3;
        }
    }*/

    private class MovieAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            View view = null;

            if (type == 0) {
                view = new MovieViewListBig(parent.getContext());
            } else if (type == 1) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
                view.getLayoutParams().height = (int) (parent.getWidth() / 2 * 1.5f);
            }

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            MovieRealm movie = movies.get(position);

            if (type == 0) {
                MovieViewListBig view = (MovieViewListBig) holder.itemView;
                view.setPoster(movie.posterPath)
                    .setTitle(movie.title)
                    .setRating(String.valueOf(movie.voteAverage))
                    .setVoteCount(String.valueOf(movie.voteCount))
                    .setReleaseDate(movie.releaseDate)
                    .setOverview(movie.overview)
                    .setDivider(position != movies.size() - 1);
            } else if (type == 1) {
                View view = holder.itemView;

                CardView cardView = view.findViewById(R.id.cardview);
                ImageView posterImage = view.findViewById(R.id.poster_image);

                Glide.with(holder.itemView.getContext())
                        .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), movie.posterPath))
                        .thumbnail(0.1F)
                        .into(posterImage);

                /*RequestOptions options = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);
                Glide.with(view.getContext()).asBitmap().load(String.format(Locale.US, Url.TMDB_IMAGE, "w342", movie.posterPath))
                        .apply(options)
                        .into(new BitmapImageViewTarget(posterImage) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                super.onResourceReady(bitmap, transition);
                                //Palette.from(bitmap).generate(palette -> cardView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.primary)));
                            }
                        });*/
            }
        }

        @Override
        public int getItemCount() {
            return movies != null ? movies.size() : 0;
        }

        @Override
        public int getItemViewType(int position) {
            return AndroidUtils.viewType();
        }
    }
}