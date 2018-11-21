package org.michaelbel.moviemade.ui.base;

import android.content.Intent;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.data.dao.Keyword;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.moxy.MvpAppCompatActivity;
import org.michaelbel.moviemade.ui.modules.favorite.FavoriteActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewActivity;
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewsActivity;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersActivity;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

@SuppressWarnings("registered")
public class BaseActivity extends MvpAppCompatActivity implements BaseMvp, MediaMvp {

    @Override
    public void startFragment(@NonNull Fragment fragment, @NonNull View container) {
        getSupportFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
    }

    @Override
    public void startFragment(@NonNull Fragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment).commitAllowingStateLoss();
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

//--MediaMvp--------------------------------------------------------------------------------------

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
    public void startFave(int accountId) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        intent.putExtra(IntentsKt.ACCOUNT_ID, accountId);
        startActivity(intent);
    }

    @Override
    public void startKeywords(@NotNull Movie movie) {

    }

    @Override
    public void startKeyword(@NotNull Keyword keyword) {

    }
}