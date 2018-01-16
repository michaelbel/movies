package org.michaelbel.moviemade.mvp.base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;

import org.michaelbel.moviemade.GenreActivity;
import org.michaelbel.moviemade.GenresActivity;
import org.michaelbel.moviemade.MovieActivity;
import org.michaelbel.moviemade.PersonActivity;
import org.michaelbel.moviemade.ReviewActivity;
import org.michaelbel.moviemade.TrailersActivity;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.Genre;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.rest.model.Trailer;

import java.util.ArrayList;

@SuppressWarnings("registered")
public class BaseActivity extends MvpAppCompatActivity implements BaseModel, MediaModel {

    @Override
    public void startFragment(Fragment fragment, View container) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container.getId(), fragment)
                .commit();
    }

    @Override
    public void startFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    public void startFragment(Fragment fragment, View container, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container.getId(), fragment)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void startFragment(Fragment fragment, int containerId, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void finishFragment() {
        getSupportFragmentManager()
                .popBackStack();
    }

    @Override
    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void startMovie(MovieRealm movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movieRealm", movie);
        startActivity(intent);
    }

    @Override
    public void startPerson(Cast person) {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    @Override
    public void startReview(Review review, Movie movie) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("review", review);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void startReview(Review review, MovieRealm movie) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("review", review);
        intent.putExtra("movieRealm", movie);
        startActivity(intent);
    }

    @Override
    public void startTrailers(Movie movie, ArrayList<Trailer> list) {
        Intent intent = new Intent(this, TrailersActivity.class);
        intent.putExtra("movie", movie);
        intent.putExtra("list", list);
        startActivity(intent);
    }

    @Override
    public void startGenre(Genre genre) {
        Intent intent = new Intent(this, GenreActivity.class);
        intent.putExtra("genre", genre);
        startActivity(intent);
    }

    @Override
    public void startGenres(ArrayList<Genre> list) {
        Intent intent = new Intent(this, GenresActivity.class);
        intent.putExtra("list", list);
        startActivity(intent);
    }
}