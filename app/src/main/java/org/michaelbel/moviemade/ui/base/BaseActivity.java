package org.michaelbel.moviemade.ui.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.entity.Keyword;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.data.entity.Review;
import org.michaelbel.moviemade.ui.modules.favorites.FavoriteActivity;
import org.michaelbel.moviemade.ui.modules.keywords.activity.KeywordActivity;
import org.michaelbel.moviemade.ui.modules.keywords.activity.KeywordsActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;
import org.michaelbel.moviemade.ui.modules.recommendations.RcmdMoviesActivity;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewActivity;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewsActivity;
import org.michaelbel.moviemade.ui.modules.similar.SimilarMoviesActivity;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersActivity;
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistActivity;
import org.michaelbel.moviemade.utils.IntentsKt;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings("registered")
public abstract class BaseActivity extends AppCompatActivity implements BaseContract, BaseContract.BaseView, BaseContract.MediaView {

    private Unbinder unbinder;

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

//--------------------------------------------------------------------------------------------------

    @NotNull
    @Override
    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences(SharedPrefsKt.SP_NAME, MODE_PRIVATE);
    }

//--BaseView----------------------------------------------------------------------------------------

    @Override
    public void startFragment(@NonNull Fragment fragment, @NonNull View container) {
        getSupportFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
    }

    @Override
    public void startFragment(@NonNull Fragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment).commit();
    }

    @Override
    public void startFragment(@NonNull Fragment fragment, @NonNull View container, @NonNull String tag) {
        getSupportFragmentManager().beginTransaction().replace(container.getId(), fragment).addToBackStack(tag).commit();
    }

    @Override
    public void startFragment(@NonNull Fragment fragment, int containerId, @NonNull String tag) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment).addToBackStack(tag).commit();
    }

    @Override
    public void finishFragment() {
        getSupportFragmentManager().popBackStack();
    }

//--MediaView---------------------------------------------------------------------------------------

    @Override
    public void startMovie(@NonNull Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(IntentsKt.MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void startTrailers(@NonNull Movie movie) {
        Intent intent = new Intent(this, TrailersActivity.class);
        intent.putExtra(IntentsKt.MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void startReview(@NotNull Review review, @NotNull Movie movie) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra(IntentsKt.MOVIE, movie);
        intent.putExtra(IntentsKt.REVIEW, review);
        startActivity(intent);
    }

    @Override
    public void startReviews(@NonNull Movie movie) {
        Intent intent = new Intent(this, ReviewsActivity.class);
        intent.putExtra(IntentsKt.MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void startFavorites(int accountId) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        intent.putExtra(IntentsKt.ACCOUNT_ID, accountId);
        startActivity(intent);
    }

    @Override
    public void startWatchlist(int accountId) {
        Intent intent = new Intent(this, WatchlistActivity.class);
        intent.putExtra(IntentsKt.ACCOUNT_ID, accountId);
        startActivity(intent);
    }

    @Override
    public void startKeywords(@NotNull Movie movie) {
        Intent intent = new Intent(this, KeywordsActivity.class);
        intent.putExtra(IntentsKt.MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void startKeyword(@NotNull Keyword keyword) {
        Intent intent = new Intent(this, KeywordActivity.class);
        intent.putExtra(IntentsKt.KEYWORD, keyword);
        startActivity(intent);
    }

    @Override
    public void startSimilarMovies(@NotNull Movie movie) {
        Intent intent = new Intent(this, SimilarMoviesActivity.class);
        intent.putExtra(IntentsKt.MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void startRcmdsMovies(@NotNull Movie movie) {
        Intent intent = new Intent(this, RcmdMoviesActivity.class);
        intent.putExtra(IntentsKt.MOVIE, movie);
        startActivity(intent);
    }
}