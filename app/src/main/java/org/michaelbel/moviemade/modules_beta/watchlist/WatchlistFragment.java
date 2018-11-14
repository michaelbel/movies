package org.michaelbel.moviemade.modules_beta.watchlist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.R;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class WatchlistFragment extends Fragment {

    private MovieAdapter adapter;
    private MainActivity activity;
    private List<MovieRealm> movies = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
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

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        movies.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MovieRealm> results = realm.where(MovieRealm.class).equalTo("watching", true).findAll();
        if (results.isEmpty()) {
            onLoadError();
        } else {
            movies.addAll(results);
            Collections.reverse(movies);
            onLoadSuccessful();
        }
        adapter.notifyDataSetChanged();
        realm.close();
    }

    private void onLoadError() {
        Log.e("222", "On Load Error");
        progressBar.setVisibility(View.GONE);
        emptyView.setMode(EmptyViewMode.MODE_NO_MOVIES);
    }

    private void onLoadSuccessful() {
        Log.e("222", "On Load Successful");
        progressBar.setVisibility(View.GONE);
    }

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

                RequestOptions options = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

                Glide.with(view.getContext()).asBitmap()
                     .load(String.format(Locale.US, ConstantsKt.TMDB_IMAGE, "w342", movie.posterPath)).apply(options)
                     .into(new BitmapImageViewTarget(posterImage) {
                         @Override
                         public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                             super.onResourceReady(bitmap, transition);
                             Palette.from(bitmap).generate(palette -> cardView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.primary)));
                         }
                     });
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