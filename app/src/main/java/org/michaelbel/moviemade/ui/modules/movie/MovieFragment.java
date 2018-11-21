package org.michaelbel.moviemade.ui.modules.movie;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.constants.CodeKt;
import org.michaelbel.moviemade.data.constants.Genres;
import org.michaelbel.moviemade.data.dao.Genre;
import org.michaelbel.moviemade.data.dao.MarkFave;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.receivers.NetworkChangeListener;
import org.michaelbel.moviemade.receivers.NetworkChangeReceiver;
import org.michaelbel.moviemade.ui.modules.movie.views.RatingView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.Browser;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.SharedPrefsKt;
import org.michaelbel.moviemade.utils.SpannableUtil;
import org.michaelbel.moviemade.utils.TmdbConfigKt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;

public class MovieFragment extends MvpAppCompatFragment implements MovieMvp, View.OnClickListener, NetworkChangeListener {

    private boolean favorite;
    private Menu actionMenu;
    private MenuItem menu_share;
    private MenuItem menu_tmdb;
    private MenuItem menu_imdb;
    private MenuItem menu_homepage;

    private View view;
    private String imdbId;
    private String homepage;
    private String posterPath;
    private boolean connectionError;
    private Unbinder unbinder;
    private MovieActivity activity;
    private NetworkChangeReceiver networkChangeReceiver;
    private GenresAdapter adapter;

    //@Inject MoviesDatabase moviesDatabase;
    @Inject SharedPreferences sharedPreferences;
    @InjectPresenter MoviePresenter presenter;

    @BindView(R.id.user_avatar) AppCompatImageView posterImage;
    @BindView(R.id.info_layout) LinearLayoutCompat infoLayout;
    @BindView(R.id.rating_view) RatingView ratingView;
    @BindView(R.id.rating_text) AppCompatTextView ratingText;
    @BindView(R.id.vote_count_text) AppCompatTextView voteCountText;
    @BindView(R.id.date_layout) LinearLayoutCompat releaseDateLayout;
    @BindView(R.id.release_date_icon) AppCompatImageView releaseDateIcon;
    @BindView(R.id.release_date_text) AppCompatTextView releaseDateText;
    @BindView(R.id.runtime_icon) AppCompatImageView runtimeIcon;
    @BindView(R.id.runtime_text) AppCompatTextView runtimeText;
    @BindView(R.id.lang_layout) LinearLayoutCompat langLayout;
    @BindView(R.id.lang_icon) AppCompatImageView langIcon;
    @BindView(R.id.lang_text) AppCompatTextView langText;
    @BindView(R.id.title_layout) LinearLayoutCompat titleLayout;
    @BindView(R.id.title_text) AppCompatTextView titleText;
    @BindView(R.id.tagline_text) AppCompatTextView taglineText;
    @BindView(R.id.overview_text) AppCompatTextView overviewText;
    @BindView(R.id.fave_layout) LinearLayoutCompat faveLayout;
    @BindView(R.id.fave_icon) AppCompatImageView faveIcon;
    @BindView(R.id.fave_text) AppCompatTextView faveText;
    @BindView(R.id.trailers_text) AppCompatTextView trailersText;
    @BindView(R.id.reviews_text) AppCompatTextView reviewsText;
    @BindView(R.id.crew_layout) LinearLayoutCompat crewLayout;
    @BindView(R.id.starring_text) AppCompatTextView starringText;
    @BindView(R.id.directed_text) AppCompatTextView directedText;
    @BindView(R.id.written_text) AppCompatTextView writtenText;
    @BindView(R.id.produced_text) AppCompatTextView producedText;
    @BindView(R.id.genres_recycler_view) RecyclerListView genresRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MovieActivity) getActivity();
        Moviemade.getComponent().injest(this);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        activity.registerReceiver(networkChangeReceiver, new IntentFilter(NetworkChangeReceiver.INTENT_ACTION));
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        actionMenu = menu;
        menu_share = menu.add(R.string.share).setIcon(R.drawable.ic_anim_share).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu_tmdb = menu.add(R.string.view_on_tmdb).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == menu_share) {
            Drawable icon = actionMenu.getItem(0).getIcon();
            if (icon instanceof Animatable) {
                ((Animatable) icon).start();
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, TmdbConfigKt.TMDB_MOVIE, activity.movie.getId()));
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        } else if (item == menu_tmdb) {
            Browser.INSTANCE.openUrl(activity, String.format(Locale.US, TmdbConfigKt.TMDB_MOVIE, activity.movie.getId()));
        } else if (item == menu_imdb) {
            Browser.INSTANCE.openUrl(activity, String.format(Locale.US, TmdbConfigKt.IMDB_MOVIE, imdbId));
        } else if (item == menu_homepage) {
            Browser.INSTANCE.openUrl(activity, homepage);
        }

        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this, view);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        runtimeText.setText(R.string.loading_status);
        runtimeIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(activity, R.drawable.ic_clock, ContextCompat.getColor(activity, R.color.iconActive)));

        taglineText.setText(R.string.loading_tagline);

        starringText.setText(SpannableUtil.boldAndColoredText(getString(R.string.starring), getString(R.string.starring, getString(R.string.loading))));
        directedText.setText(SpannableUtil.boldAndColoredText(getString(R.string.directed), getString(R.string.directed, getString(R.string.loading))));
        writtenText.setText(SpannableUtil.boldAndColoredText(getString(R.string.written), getString(R.string.written, getString(R.string.loading))));
        producedText.setText(SpannableUtil.boldAndColoredText(getString(R.string.produced), getString(R.string.produced, getString(R.string.loading))));

        posterImage.setOnClickListener(this);

        faveLayout.setOnClickListener(this);
        faveLayout.setVisibility(View.GONE);

        trailersText.setOnClickListener(this);
        reviewsText.setOnClickListener(this);

        adapter = new GenresAdapter();
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(activity).setOrientation(ChipsLayoutManager.HORIZONTAL).build();

        genresRecyclerView.setAdapter(adapter);
        genresRecyclerView.setLayoutManager(chipsLayoutManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(networkChangeReceiver);
        presenter.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setPoster(@NotNull String posterPath) {
        this.posterPath = posterPath;

        posterImage.setVisibility(VISIBLE);
        Glide.with(activity).load(String.format(Locale.US, TmdbConfigKt.TMDB_IMAGE, "w342", posterPath)).thumbnail(0.1F).into(posterImage);
    }

    @Override
    public void setMovieTitle(@NotNull String title) {
        titleText.setText(title);
    }

    @Override
    public void setOverview(@NotNull String overview) {
        if (TextUtils.isEmpty(overview)) {
            overviewText.setText(R.string.no_overview);
            return;
        }

        overviewText.setText(overview);
    }

    @Override
    public void setVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingText.setText(String.valueOf(voteAverage));
    }

    @Override
    public void setVoteCount(int voteCount) {
        voteCountText.setText(String.valueOf(voteCount));
    }

    @Override
    public void setReleaseDate(@NotNull String releaseDate) {
        if (TextUtils.isEmpty(releaseDate)) {
            infoLayout.removeView(releaseDateLayout);
            return;
        }

        releaseDateIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(activity, R.drawable.ic_calendar, ContextCompat.getColor(activity, R.color.iconActive)));
        releaseDateText.setText(releaseDate);
    }

    @Override
    public void setOriginalLanguage(@NotNull String originalLanguage) {
        if (TextUtils.isEmpty(originalLanguage)) {
            infoLayout.removeView(langLayout);
            return;
        }

        langIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(activity, R.drawable.ic_earth, ContextCompat.getColor(activity, R.color.iconActive)));
        langText.setText(originalLanguage);
    }

    @Override
    public void setRuntime(@NotNull String runtime) {
        runtimeText.setText(runtime);
    }

    @Override
    public void setTagline(@NotNull String tagline) {
        if (TextUtils.isEmpty(tagline)) {
            titleLayout.removeView(taglineText);
            return;
        }

        taglineText.setText(tagline);
    }

    @Override
    public void setURLs(@NotNull String imdbId, @NotNull String homepage) {
        this.imdbId = imdbId;
        this.homepage = homepage;

        if (!TextUtils.isEmpty(imdbId)) {
            menu_imdb = actionMenu.add(R.string.view_on_imdb).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        }

        if (!TextUtils.isEmpty(homepage)) {
            menu_homepage = actionMenu.add(R.string.view_homepage).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        }
    }

    @Override
    public void setStates(boolean fave) {
        favorite = fave;
        faveLayout.setVisibility(VISIBLE);
        faveIcon.setImageResource(fave ? R.drawable.ic_heart : R.drawable.ic_heart_outline);
        faveText.setText(fave ? R.string.remove : R.string.add);
    }

    @Override
    public void onFavoriteChanged(@NotNull MarkFave markFave) {
        switch (markFave.getStatusCode()) {
            case CodeKt.ADDED:
                faveIcon.setImageResource(R.drawable.ic_heart);
                faveText.setText(R.string.remove);
                favorite = true;
                break;
            case CodeKt.DELETED:
                faveIcon.setImageResource(R.drawable.ic_heart_outline);
                faveText.setText(R.string.add);
                favorite = false;
                //sendEvent();
                break;
        }
    }

    @Override
    public void setCredits(@NotNull String casts, @NotNull String directors, @NotNull String writers, @NotNull String producers) {
        starringText.setText(SpannableUtil.boldAndColoredText(getString(R.string.starring), getString(R.string.starring, casts)));
        directedText.setText(SpannableUtil.boldAndColoredText(getString(R.string.directed), getString(R.string.directed, directors)));
        writtenText.setText(SpannableUtil.boldAndColoredText(getString(R.string.written), getString(R.string.written, writers)));
        producedText.setText(SpannableUtil.boldAndColoredText(getString(R.string.produced), getString(R.string.produced, producers)));
    }

    @Override
    public void setGenres(@NonNull List<Integer> genreIds) {
        List<Genre> list = new ArrayList<>();
        for (int id : genreIds) {
            list.add(Genres.INSTANCE.getGenreById(id));
        }

        adapter.setGenres(list);
    }

    @Override
    public void setConnectionError() {
        Snackbar.make(view, R.string.no_connection, Snackbar.LENGTH_SHORT).show();
        connectionError = true;
    }

    @Override
    public void showComplete(@NotNull Movie movie) {
        connectionError = false;
    }

    @Override
    public void onClick(View v) {
        if (v == faveLayout) {
            presenter.markAsFavorite((Moviemade) activity.getApplication(), sharedPreferences.getInt(SharedPrefsKt.KEY_ACCOUNT_ID, 0), activity.movie.getId(), !favorite);
        } else if (v == posterImage) {
            activity.imageAnimator = GestureTransitions.from(posterImage).into(activity.fullImage);
            activity.imageAnimator.addPositionUpdateListener((position, isLeaving) -> {
                activity.fullBackground.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
                activity.fullBackground.setAlpha(position);

                activity.fullImageToolbar.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
                activity.fullImageToolbar.setAlpha(position);

                activity.fullImage.setVisibility(position == 0f && isLeaving ? View.INVISIBLE : View.VISIBLE);

                Glide.with(activity).load(String.format(Locale.US, TmdbConfigKt.TMDB_IMAGE, "original", posterPath)).thumbnail(0.1F).into(activity.fullImage);

                if (position == 0f && isLeaving) {
                    activity.showSystemStatusBar(true);
                }
            });
            activity.fullImage.getController().getSettings()
                .setGravity(Gravity.CENTER)
                .setZoomEnabled(true)
                .setAnimationsDuration(300L)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setFitMethod(Settings.Fit.INSIDE)
                .setPanEnabled(true)
                .setRestrictRotation(false)
                .setOverscrollDistance(activity, 32F, 32F)
                .setOverzoomFactor(Settings.OVERZOOM_FACTOR)
                .setFillViewport(true);
            activity.imageAnimator.enterSingle(true);
        } else if (v == trailersText) {
            activity.startTrailers(activity.movie);
        } else if (v == reviewsText) {
            activity.startReviews(activity.movie);
        }
    }

    @Override
    public void onNetworkChanged() {
        if (connectionError) {
            presenter.loadDetails((Moviemade) activity.getApplication(), activity.movie.getId());
        }
    }

    /*private void sendEvent() {
        ((Moviemade) activity.getApplication()).rxBus2.send(new Events.DeleteMovieFromFavorite(activity.movie.getId()));
    }*/
}